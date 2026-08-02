# StubCall

Маленькая Kotlin/JVM библиотека для ручных тестовых double'ов: use case'ов, callback'ов и lambda-полей. Один объект умеет возвращать значение, бросать ошибку, запоминать вызовы и проверять факт вызова/аргументы.

Обычный JVM-модуль (без Kotlin Multiplatform) — подключается и в backend, и в Android-проекты одной и той же зависимостью.

## Пример

```kotlin
class StubGetUserUseCase(user: User) : GetUserUseCase {
    val invoke: StubCall1<String, User> = StubCall1.value(user)
    override fun invoke(id: String): User = invoke(id)
}

val getUser = StubGetUserUseCase(defaultUser)

getUser.invoke.calledOnce()
getUser.invoke.calledWith("42")
getUser.invoke.willReturn(otherUser)
getUser.invoke.willThrow(NetworkError())
```

## Арность и фабрики

У каждого класса `StubCall0`..`StubCall6` есть свои статические `value(x)` и `unit()` — фабрику вызываем через класс нужной арности. Для `StubCall0` тип результата выводится сам. Для `StubCall1`..`StubCall6` понадобится явная аннотация типа на `val`, потому что типы аргументов нигде не встречаются в самом вызове фабрики:

```kotlin
// 0 аргументов — тип выводится автоматически
val invoke = StubCall0.value(settings)                       // StubCall0<Settings>

// 1 аргумент
val invoke: StubCall1<String, User> = StubCall1.value(user)  // явная аннотация обязательна

// 2 аргумента, результат Unit
val invoke: StubCall2<String, Int, Unit> = StubCall2.unit()
override fun invoke(accountId: String, amount: Int) = invoke(accountId, amount)

// возвращает Unit, без аргументов
val invoke = StubCall0.unit()                                 // StubCall0<Unit>
override fun invoke() = invoke()
```

Так же есть `StubCall3.value`..`StubCall6.value` / `StubCall3.unit`..`StubCall6.unit` — вплоть до шести аргументов.

## API каждого StubCallN

- `calledOnce()` — падает с `StubCallAssertionError`, если вызовов не было или было больше одного.
- `notCalled()` — падает, если хотя бы один вызов был.
- `calledWith(a, b, ...)` — падает, если среди записанных вызовов нет вызова с такими аргументами (недоступно у `StubCall0`, у него нет аргументов).
- `callCount` — сколько раз объект вызывали.
- `willReturn(value)` — меняет значение, которое будут возвращать следующие вызовы.
- `willThrow(error)` — следующие вызовы будут бросать `error` (сам вызов при этом всё равно фиксируется).

## Подключение

Пока библиотека не опубликована в Maven Central — подключайте её как composite build:

```kotlin
// settings.gradle.kts потребляющего проекта
includeBuild("../stub-call")
```

или соберите и опубликуйте в локальный Maven-репозиторий:

```
./gradlew publishToMavenLocal
```

## Что осознанно не реализовано в этой версии

- Проверка порядка вызовов между разными стабами (order checking).
- suspend/coroutine-функции.
- Matcher DSL (`any()`, `argThat {}`) — сравнение аргументов только через `equals()`.
- Разные ответы по номеру вызова (`willReturn(x).thenReturn(y)`).
- "Красивое" сравнение массивов/коллекций в сообщениях об ошибках — для `Array` возможны ложные срабатывания из-за сравнения по ссылке.
