---
name: kmp-migration
description: "Поэтапный перевод проекта с Android (Hilt, Jetpack Compose) на Kotlin Multiplatform: Compose Multiplatform, kotlin-inject, Ktor client. Используй когда пользователь просит «переведи на KMP», «перенеси модуль в commonMain», «замени Hilt на kotlin-inject», «Compose Multiplatform», «подключи Ktor», «добавь iOS/desktop target», «ревью KMP-миграции»."
---

# KMP Migration

Skill переводит многомодульный Android-проект на Kotlin Multiplatform без «большого взрыва»: снизу вверх,
модуль за модулем, приложение собирается и тесты зелёные после каждого шага.

Целевой стек:

| Сейчас (Android)                                  | Цель (KMP)                                                                 |
|---------------------------------------------------|----------------------------------------------------------------------------|
| `kotlin("jvm")` / `com.android.library`           | `kotlin("multiplatform")` + `com.android.kotlin.multiplatform.library`     |
| Hilt (`@HiltViewModel`, `@Module`, `@InstallIn`)  | kotlin-inject (`@Component`, `@Inject`, `@Provides`, `@IntoSet`)           |
| `hiltViewModel()`                                 | `viewModel { factory() }` из `org.jetbrains.androidx.lifecycle`            |
| Jetpack Compose (BOM `androidx.compose`)          | Compose Multiplatform (`org.jetbrains.compose`)                            |
| `androidx.navigation:navigation-compose`          | `org.jetbrains.androidx.navigation:navigation-compose` (type-safe routes)  |
| `res/values/strings.xml`, `res/drawable`          | `composeResources/values/strings.xml`, `composeResources/drawable`, `Res.*` |
| `assets/content/*.json`                           | `composeResources/files/content/*.json` + `Res.readBytes(...)`             |
| Lottie (`lottie-compose`)                         | Compottie (`io.github.alexzhirkevich:compottie`)                          |
| DataStore (Android)                               | DataStore KMP (`datastore-core` / `-preferences-core`) + `expect` путь     |
| `java.time`, `java.util.*`                        | `kotlinx-datetime`, `kotlin.random`, `kotlin.uuid`                         |
| Retrofit/OkHttp (если появятся)                   | Ktor client (`OkHttp` на Android, `Darwin` на iOS)                         |
| MockK в общих тестах                              | Ручные fakes (MockK — только JVM/Android)                                  |

## Контекст и обязательное чтение

- `README.md`: **Архитектура**, **Граф зависимостей**, **Convention-плагины**, **Структура пакетов внутри модуля**,
  **ViewModel и состояние экрана**, **Тесты**.
- `AGENTS.md`, раздел **Контекст продукта** — ограничения ТЗ, `app-modules/product-core/AGENTS.md`.
- `build-logic/src/main/kotlin/ru/lct2026/finedu/buildlogic/*` — все convention-плагины.
- `gradle/libs.versions.toml`.
- Свежая документация — через context7 MCP (kotlin-inject, Compose Multiplatform, Ktor, navigation, DataStore,
  Compottie). Версии библиотек перед добавлением сверяй с Maven Central / Google Maven — не бери из памяти.
- Для перевода Android-модулей на AGP 9 KMP-плагин используй официальный skill JetBrains
  `kotlin-tooling-agp9-migration` из https://github.com/Kotlin/kotlin-agent-skills как справочник.

## Стоп-условия (сначала спроси пользователя)

1. **Ktor и сеть.** ТЗ запрещает сетевые запросы. Ktor можно подключить как инфраструктуру `data`-слоя
   (клиент за интерфейсом репозитория из `domain`), но **любой реальный HTTP-вызов в продуктовом коде** — изменение
   ТЗ. Без явного подтверждения пользователя не добавляй эндпоинты, `INTERNET`-permission и загрузку по URL.
   Контент по-прежнему читается локально из ресурсов.
2. **Новые targets** (iOS, desktop, wasm). Не включай target «посмотреть, соберётся ли» — только по запросу.
   Каждый target умножает время CI.
3. **kotlin-inject-anvil.** По умолчанию — чистый kotlin-inject с явными компонентами (см. ниже). Переход на
   `kotlin-inject-anvil` (`@ContributesTo`, `@ContributesBinding`, `@MergeComponent`) — только по решению пользователя.
4. **Изменение договорённостей README.** Миграция меняет разделы **Архитектура**, **Convention-плагины**,
   **ViewModel и состояние экрана**, **Compose**, **Тесты** (Hilt → kotlin-inject, `strings.xml` →
   `composeResources`, правила тестов). Перед коммитом каждого этапа предложи пользователю обновлённый текст README.
5. **Тесты в `commonTest`** требуют `kotlin.test`, а правило проекта — JUnit4 `org.junit.Test`. Пока нет второго
   target'а, держи тесты в `androidHostTest`/`jvmTest` на JUnit4. Перенос в `commonTest` — только после
   согласования правила с пользователем.

## Порядок этапов

Каждый этап — отдельный коммит. После каждого: `./gradlew spotlessApply detekt test lintVitalRelease` и
`:app:assembleDebug`. Если что-то красное — чини до перехода к следующему этапу.

```
0. Version catalog + convention-плагины KMP          → verify: build-logic собирается, модули не тронуты
1. product-core:domain и features/*/domain → KMP      → verify: test зелёные, нет java.*/android.* в commonMain
2. Hilt → kotlin-inject во всём проекте (ещё Android) → verify: app запускается, все экраны открываются
3. product-core:data и features/*/data → KMP          → verify: ContentParserTest зелёный, профиль сохраняется
4. product-core:navigation:api, product-core:ui → CMP → verify: превью/компонентные тесты, тема и шрифт на месте
5. features/*/ui → Compose Multiplatform              → verify: component-тесты, e2e на эмуляторе
6. (по запросу) Ktor-клиент                            → verify: стоп-условие 1 подтверждено
7. (по запросу) новые targets: iOS / desktop          → verify: сборка target'а, общий код без правок
```

Hilt убирается **до** переноса `data`/`ui`, потому что Hilt — Android-only и блокирует `commonMain`.

## Этап 0. Convention-плагины

Добавь в `libs.versions.toml` плагины `kotlin-multiplatform`, `android-kotlin-multiplatform-library`,
`compose-multiplatform` (`org.jetbrains.compose`) и библиотеки по таблице выше. Затем — KMP-версии
convention-плагинов рядом со старыми (старые удаляй, когда перейдёт последний модуль):

| Новый плагин                         | Заменяет                              | Содержимое                                               |
|--------------------------------------|---------------------------------------|----------------------------------------------------------|
| `convention.kmp-library`             | `convention.kotlin-jvm`               | `kotlin("multiplatform")` + android-kmp-library + `jvm()` для тестов при необходимости |
| `convention.kmp-feature-domain`      | `convention.kotlin-feature-domain`    | `kmp-library` + `product-core:domain` (`api`) + coroutines |
| `convention.kmp-feature-data`        | `convention.android-feature-data`     | `kmp-library` + `kotlin-inject` (KSP) + serialization    |
| `convention.kmp-feature-ui`          | `convention.android-feature-ui`       | `kmp-library` + Compose Multiplatform + `kotlin-inject` + product-core ui/navigation |
| `convention.kotlin-inject`           | `convention.hilt`                     | KSP + `kotlin-inject-runtime-kmp` + compiler             |

Шаблон KMP-модуля (Android-only, пока нет других targets):

```kotlin
// build-logic: KmpLibraryConventionPlugin
with(pluginManager) {
    apply("org.jetbrains.kotlin.multiplatform")
    apply("com.android.kotlin.multiplatform.library")
}
extensions.configure<KotlinMultiplatformExtension> {
    android {
        namespace = "ru.lct2026.finedu." + path.removePrefix(":app-modules:").replace(':', '.').replace('-', '.')
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest { }               // JVM unit-тесты Android target'а (замена src/test)
    }
}
```

Исходники переезжают: `src/main/kotlin` → `src/commonMain/kotlin` (или `androidMain`, если код платформенный),
`src/test/kotlin` → `src/androidHostTest/kotlin` (пока JUnit4). Kotlin package не меняется — он по-прежнему
повторяет путь модуля (`README.md` → **Структура пакетов внутри модуля**).

Kover, detekt и spotless должны видеть новые source sets — проверь `koverXmlReport` после первого модуля.

## Этап 1. Domain

- Модули уже чистый Kotlin — переносятся почти без правок.
- Запрещено в `commonMain`: `java.*`, `android.*`, `javax.inject.*`, `String.format`, `System.currentTimeMillis()`.
  Замены: `kotlinx-datetime` (`Clock.System.now()`), `kotlin.random.Random`, интерполяция строк.
- Если domain-логика зависит от времени — инжектируй `Clock` (из `kotlin.time`/`kotlinx-datetime`), а не вызывай
  `now()` напрямую: так тесты экономики остаются детерминированными.
- DI-аннотаций в `domain` по-прежнему нет: use case'ы создаются через `@Provides` в `ui/di` (см. этап 2).
- Игровая экономика покрыта тестами обязательно — тесты переносятся вместе с кодом и должны остаться зелёными.

## Этап 2. Hilt → kotlin-inject

Принцип: каждый модуль, который раньше давал Hilt `@Module`, теперь даёт **interface-компонент** с `@Provides`;
`:app` собирает их в один `@Component`. Граф Gradle-зависимостей не меняется: `ui` по-прежнему не видит `data`.

`data` — привязки реализаций:

```kotlin
// features/shop/data/.../di/ShopDataComponent.kt
interface ShopDataComponent {
    @Provides
    fun ShopRepositoryImpl.bind(): ShopRepository = this
}

// реализация остаётся internal
@Inject
internal class ShopRepositoryImpl(private val game: GameRepository) : ShopRepository
```

`ui` — use case'ы, ViewModel и навигация:

```kotlin
// features/shop/ui/.../di/ShopUiComponent.kt
interface ShopUiComponent {
    @Provides
    fun provideBuyItem(repository: ShopRepository): BuyItemUseCase = BuyItemUseCase(repository)

    @Provides
    @IntoSet
    fun ShopNavigationContribution.bindNavigation(): FeatureNavigationContribution = this
}

@Inject
internal class ShopViewModel(
    private val buyItem: BuyItemUseCase,
) : StatefulViewModel<ShopUiState>(ShopUiState())
```

`app` — корневой компонент:

```kotlin
@AppScope
@Component
abstract class AppComponent(
    @get:Provides val context: Context,          // платформенные зависимости — параметрами компонента
) : ProductCoreDataComponent,
    ShopDataComponent, ShopUiComponent
    /* … остальные фичи … */ {
    abstract val navigationContributions: Set<FeatureNavigationContribution>
}

// Application
val component = AppComponent::class.create(applicationContext)
```

- Синглтоны (`@Singleton` в Hilt) → свой scope-annotation `@AppScope` (`@Scope annotation class AppScope`) в
  `product-core`, ставится на компонент и на `@Inject`-класс/`@Provides`.
- `@HiltViewModel` + `hiltViewModel()` → в `NavigationContribution` получаем фабрику из компонента:

  ```kotlin
  @Inject
  class ShopNavigationContribution(
      private val shopViewModel: () -> ShopViewModel,
  ) : FeatureNavigationContribution {
      override fun NavGraphBuilder.register(navController: NavController) {
          composable<FinEduRoute.Shop> {
              ShopRoute(onBack = navController::popBackStack, viewModel = viewModel { shopViewModel() })
          }
      }
  }
  ```

  `XxxRoute` больше не имеет дефолта `= hiltViewModel()`; ViewModel передаётся параметром.
  `SavedStateHandle` → `@Assisted handle: SavedStateHandle` + `viewModel { factory(createSavedStateHandle()) }`.
- `@HiltAndroidApp`, `@AndroidEntryPoint`, `hilt-*` зависимости, `convention.hilt`, `HiltConventionPlugin` удаляются
  в конце этапа. Проверь `grep -rn "dagger\|hilt" --include=*.kt --include=*.kts .` → пусто.
- Не используй рефлексию для поиска компонентов или ViewModel — kotlin-inject генерирует код через KSP.
- Подключение KSP в KMP-модуле: `add("kspAndroid", libs.kotlin.inject.compiler)` (для каждого target'а), либо
  `kspCommonMainMetadata` — но **не оба сразу** (ошибка Redeclaration для `@Component`).

## Этап 3. Data

- DataStore: реализация переезжает в `commonMain`; путь к файлу — через интерфейс/`expect` с `androidMain`
  реализацией (`context.filesDir`). `Context` приходит параметром `AppComponent`.
- JSON-контент `product-core/data/src/main/assets/content/` → `src/commonMain/composeResources/files/content/`;
  чтение — `Res.readBytes("files/content/shop.json").decodeToString()` + `kotlinx.serialization`.
  `ContentParserTest` переносится и должен проверять те же файлы.
- DTO, мапперы и `RepositoryImpl` остаются `internal` в `data` — правила изоляции слоёв не меняются.
- Если появится Room — Room KMP (`androidx.room` 2.7+, `BundledSQLiteDriver`), не SQLDelight: меньше переписывания.

## Этап 4–5. Compose Multiplatform

- Подключи плагины `org.jetbrains.compose` + `org.jetbrains.kotlin.plugin.compose`, зависимости — через
  `compose.runtime`, `compose.foundation`, `compose.material3`, `compose.components.resources`,
  `compose.components.uiToolingPreview`.
- Ресурсы: `res/values/strings.xml` → `src/commonMain/composeResources/values/strings.xml`;
  `stringResource(R.string.x)` → `stringResource(Res.string.x)`; `painterResource(R.drawable.x)` →
  `painterResource(Res.drawable.x)`. Префиксы имён (`ic_`, `bg_`, `anim_`) сохраняются. Тексты по-прежнему
  только в ресурсах, не в коде.
- Шрифт Commissioner → `composeResources/font/` + `Font(Res.font.commissioner_regular)` в `FinEduTheme`.
- `FinLottie` → реализация на Compottie (`rememberLottieComposition { LottieCompositionSpec.JsonString(...) }`),
  JSON из `composeResources/files/`. Dynamic properties `PetView` (`Fur`, видимость `Bowler`/`Cap`/`Headphones`)
  перепроверь на устройстве — у Compottie свой API dynamic properties. Загрузка по URL по-прежнему запрещена.
- Звук (`GameAudio`: `MediaPlayer`/`SoundPool`) — платформенный: интерфейс `SoundPlayer` в `commonMain`,
  реализация в `androidMain` модуля `app`/`product-core:ui`.
- `collectAsStateWithLifecycle`, `LocalLifecycleOwner`, `ObserveEvents` — из
  `org.jetbrains.androidx.lifecycle:lifecycle-runtime-compose` (KMP).
- `@Preview`: `org.jetbrains.compose.ui.tooling.preview.Preview` (или androidx в `androidMain`), внутри
  `FinEduPreview { }` как раньше.
- `BackHandler`, `Activity`, системные бары, `MainActivity` остаются в `androidMain`/`app`.
- Compose component-тесты: `runComposeUiTest { }` (`compose.uiTest`). Пока тесты в `androidHostTest`,
  Robolectric сохраняется (правило README); переход на `commonTest` — стоп-условие 5.
- Доступность (16sp, 48dp, `heading()`, `contentDescription`) и `FinEduTheme.reduceMotion` — без изменений.

## Этап 6. Ktor client (только после подтверждения стоп-условия 1)

```kotlin
// commonMain (data)
internal fun createHttpClient(engine: HttpClientEngine): HttpClient = HttpClient(engine) {
    install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
    expectSuccess = true
}
// androidMain: OkHttp.create();  iosMain: Darwin.create()  — engine приходит через kotlin-inject
```

- `HttpClient` — `@AppScope`, один на приложение, создаётся в `product-core:data`.
- Наружу торчат только интерфейсы `domain`; DTO и `HttpClient` не покидают `data`.
- Тесты — `MockEngine` из `ktor-client-mock`, без реальной сети.
- Никакого сбора персональных данных, аналитики, рекламных SDK — ограничения ТЗ действуют и для сети.

## Этап 7. Новые targets (только по запросу)

```kotlin
listOf(iosArm64(), iosSimulatorArm64()).forEach {
    it.binaries.framework { baseName = "Shared"; isStatic = true }
}
```

- Все зависимости должны иметь iOS-артефакты; ещё раз прогони `grep -rn "import android\.\|import java\." */src/commonMain`.
- KSP для kotlin-inject добавь для каждого нового target'а (`kspIosArm64`, `kspIosSimulatorArm64`).
- `AppComponent` для iOS создаётся в `iosMain` и отдаёт корневой `@Composable` через `ComposeUIViewController`.

## Анти-паттерны

- Один и тот же код в `src/main` и `src/commonMain` одновременно — после переноса старый файл удаляется.
- Перенос `ui` в CMP раньше, чем `domain`/`data` стабильны в KMP.
- Свой ServiceLocator/`object Graph` вместо kotlin-inject; `lateinit var component` в глобальном объекте.
- `as?`/type casts для поиска нужного компонента или ViewModel — используй типизированные фабрики.
- `expect class` там, где хватает интерфейса в `commonMain` + реализации в `androidMain`, внедрённой через DI.
- Зависимость `ui` → `data` «ради DI»: привязки собирает только `:app`.

## Чек-лист этапа

- [ ] Приложение собирается (`:app:assembleDebug`), демо-сценарий проходит на эмуляторе.
- [ ] `./gradlew spotlessApply detekt lintVitalRelease test` — зелёные; Kover ≥ 80% для нового кода.
- [ ] В `commonMain` нет `android.*`, `java.*`, `dagger.*`, `javax.inject.*`.
- [ ] Граф зависимостей из README не нарушен: `ui` ↛ `data`, фичи ↛ фичи, `domain` без DI-аннотаций.
- [ ] Перенесённые файлы удалены из старых source sets.
- [ ] Нет сетевых вызовов без подтверждённого изменения ТЗ.
- [ ] Предложен обновлённый текст README для изменённых договорённостей.

## Источники

- Структура и порядок миграции адаптированы из skill `android-to-kmp`,
  [Masum-MSNR/awesome-kotlin-multiplatform-agent-skills](https://github.com/Masum-MSNR/awesome-kotlin-multiplatform-agent-skills),
  лицензия CC BY 4.0. Изменения: Koin/SQLDelight заменены на kotlin-inject/DataStore/Room KMP, добавлены этапы
  Hilt → kotlin-inject и Compose Multiplatform, правила и ограничения этого проекта.
- kotlin-inject: https://github.com/evant/kotlin-inject (`docs/multiplatform.md`, `docs/android.md`).
- JetBrains Kotlin agent skills (AGP 9 + KMP): https://github.com/Kotlin/kotlin-agent-skills.
