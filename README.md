# rust-lang-kotlin

[![GitHub link](https://img.shields.io/badge/GitHub-KotlinMania%2Frust--lang--kotlin-blue.svg)](https://github.com/KotlinMania/rust-lang-kotlin)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.kotlinmania/rust-lang-kotlin)](https://central.sonatype.com/artifact/io.github.kotlinmania/rust-lang-kotlin)
[![Build status](https://img.shields.io/github/actions/workflow/status/KotlinMania/rust-lang-kotlin/ci.yml?branch=main)](https://github.com/KotlinMania/rust-lang-kotlin/actions)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-7F52FF.svg?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Platforms](https://img.shields.io/badge/platforms-JVM%20%7C%20Android%20%7C%20Apple%20%7C%20Linux%20%7C%20Windows%20%7C%20JS%20%7C%20Wasm-lightgrey.svg)](#supported-targets)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE-MIT)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE-APACHE)

A Kotlin Multiplatform source port of the Rust standard library family from
[`rust-lang/rust`](https://github.com/rust-lang/rust): `core`, `alloc`, `std`, and the
support crates they are built on (`compiler_builtins`, `libm`, `core_arch`, `std_detect`,
`backtrace`, `unwind`, `panic_abort`, `panic_unwind`).

It is the foundation layer for the KotlinMania Rust ports. A ported crate that needs
`Vec`, `Option`, `Result`, `Layout`, raw pointers, or atomics imports them from here
instead of re-inventing them.

**Original project:** all design credit and algorithmic intent belong to the Rust project
contributors. This repository is a translation of their work into Kotlin, with no
JVM-only or third-party Java dependencies in common code.

---

## Status

Version `0.2.0` publishes the verified `core` + `alloc` release kernel (`99` Kotlin
source files on `main`, zero external dependencies beyond `kotlin-stdlib`) to Maven Central
as `io.github.kotlinmania:rust-lang-kotlin:0.2.0`. The release kernel passes all 17
KotlinMania targets, Android emulator device tests, JNI/N-API/cinterop FFI tests, and
Debug/Release Swift Export smoke tests.

The broader `std`, `core_arch`, `core_simd`, `compiler_builtins`, `libm`, `std_detect`,
and unwinding/panic modules are actively developed on the `development` branch and will be
promoted in subsequent releases. Treat pre-`1.0` APIs as evolving.

---

## Installation

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.kotlinmania:rust-lang-kotlin:0.2.0")
}
```

### Gradle (Groovy DSL)

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'io.github.kotlinmania:rust-lang-kotlin:0.2.0'
}
```

### Maven (`pom.xml`)

```xml
<dependency>
    <groupId>io.github.kotlinmania</groupId>
    <artifactId>rust-lang-kotlin</artifactId>
    <version>0.2.0</version>
</dependency>
```

---

## Supported Targets

| Platform category | Target identifiers |
|---|---|
| **JVM & Android** | `jvm` (Java 21 toolchain), `android` (AAR, API 24+) |
| **Apple** | `macosArm64`, `iosArm64`, `iosSimulatorArm64`, `tvosArm64`, `tvosSimulatorArm64`, `watchosDeviceArm64`, `watchosSimulatorArm64` |
| **Linux & Windows** | `linuxX64`, `linuxArm64`, `mingwX64` |
| **Android Native** | `androidNativeArm64`, `androidNativeX64` |
| **Web & Wasm** | `js`, `wasmJs`, `wasmWasi` |

32-bit targets and Apple x86_64 targets are not supported.

---

## Packages

Everything lives under `io.github.kotlinmania.rustlang`. Each Rust crate maps to one
package root, and each Rust module to a sub-package.

| Rust crate | Kotlin package | Contents |
|---|---|---|
| `core` | `rustlang.core` | `option`, `result`, `ptr`, `mem`, `cell`, `slice`, `str`, `iter`, `fmt`, `num`, `cmp`, `ops`, `hash`, `sync` (atomics), `pin`, `task`, `time`, `ffi`, `intrinsics` |
| `alloc` | `rustlang.alloc` | `alloc` (`Layout`, `Global`), `rawvec`, `vec`, `boxed`, `string`, `borrow`, `rcs`, `collections`, `ffi`, `fmt` |
| `std` | `rustlang.std` | `collections`, `env`, `error`, `ffi`, `fs`, `io`, `net`, `os`, `panic`, `path`, `process`, `sync`, `sys` |
| `compiler_builtins` | `rustlang.compilerbuiltins` | Integer and float runtime builtins |
| `libm` | `rustlang.libm` | Math routines |
| `core_arch` | `rustlang.corearch` | Architecture intrinsics |
| `core_simd` | `rustlang.coresimd` | Portable SIMD |
| `std_detect` | `rustlang.stddetect` | CPU feature detection |
| `backtrace`, `unwind`, `panic_abort`, `panic_unwind` | `rustlang.backtrace`, `rustlang.unwind`, `rustlang.panicabort`, `rustlang.panicunwind` | Panic and unwinding runtime |

Naming follows Kotlin conventions: `PascalCase` types, `camelCase` functions, lowercase
packages. Rust `snake_case` names are translated (`with_capacity` becomes `withCapacity`,
`is_some` becomes `isSome`).

---

## Usage

### `Option` and `Result`

```kotlin
import io.github.kotlinmania.rustlang.core.option.Option
import io.github.kotlinmania.rustlang.core.result.Result

val some: Option<Int> = Option.Some(41)
val none: Option<Int> = Option.None()

val next = some.map { it + 1 }      // Some(42)
check(next.isSome())
check(none.isNone())
val value = none.unwrapOr(0)        // 0

val ok: Result<Int, String> = Result.Ok(7)
val err: Result<Int, String> = Result.Err("boom")
check(ok.isOk() && err.isErr())
val doubled = ok.map { it * 2 }     // Ok(14)
```

`unwrap()` and `expect(msg)` panic on the empty case, as they do in Rust.

### `Vec`

```kotlin
import io.github.kotlinmania.rustlang.alloc.vec.Vec
import io.github.kotlinmania.rustlang.alloc.vec.new
import io.github.kotlinmania.rustlang.alloc.vec.withCapacity
import io.github.kotlinmania.rustlang.alloc.vec.fromFn

val v: Vec<Int> = Vec.new()
v.push(1)
v.push(2)
check(v.len() == 2)
val last: Int? = v.pop()            // 2

val reserved: Vec<Long> = Vec.withCapacity(1024)
check(reserved.isEmpty() && reserved.capacity() >= 1024)

val squares: Vec<Int> = Vec.fromFn(8) { it * it }
```

`Vec` is backed by a `RawVec` buffer. `asPtr()` and `asMutPtr()` expose that buffer as a
`ConstPtr` / `MutPtr` for zero-copy FFI.

### Memory layout

```kotlin
import io.github.kotlinmania.rustlang.core.mem.TypeLayout
import io.github.kotlinmania.rustlang.core.mem.sizeOf
```

Kotlin erases generics, so functions that need a type's size or alignment take an
explicit `TypeLayout<T>` argument where Rust would use `size_of::<T>()` implicitly.

---

## Architecture

- **Source translation.** Rust bodies are translated to Kotlin in `commonMain`. One Rust
  module maps to one Kotlin package; crate roots and module roots are split into focused
  files rather than mirrored as re-export files.
- **Intrinsic lowering.** Only bodiless `core::intrinsics` and `core::arch` operations
  cross an FFI boundary:
  - **Native:** `cinterop` definitions under `src/native/interop/cinterop/`, with
    always-inline C headers for loads, stores, C11 atomics and SIMD, plus AArch64 and
    x86_64 assembly for 128-bit compare-exchange, high-half multiply and spin hints.
  - **JVM & Android:** JNI.
  - **JS & Wasm:** Node N-API addons under `src/js/node-*`.
- **Swift.** A Swift Export test harness lives in `src/swift/swift-test-harness/`.

Source layout:

```
src/
  common/commonMain/   translated Kotlin (all targets)
  jvm/jvmMain/         JVM actuals
  android/             Android actuals
  native/nativeMain/   Kotlin/Native actuals
  native/interop/      cinterop definitions
  js/                  JS, Wasm and N-API addons
  swift/               Swift Export test harness
```

---

## Building

Requirements: JDK 21, and Xcode on macOS for Apple targets. The Gradle wrapper (9.8.2)
and the Android SDK are provisioned by the build.

```bash
./gradlew build
```

```bash
./gradlew test
```

```bash
./gradlew publishToMavenLocal
```

Upstream Rust sources are expected under `tmp/` (git-ignored) when regenerating or
comparing against upstream. Clone `rust-lang/rust` there; it is not needed for a plain
build.

### Continuous integration

GitHub Actions workflows are manual-only. `ci.yml` runs on `workflow_dispatch`, the
per-platform workflows are reusable (`workflow_call`), and `publish.yml` runs on a
published GitHub release.

---

## Related documents

- [AGENTS.md](AGENTS.md): contributor and agent guide for the KotlinMania workspace
- [SWIFT.md](SWIFT.md): Swift Export rollout
- [ANDROID.md](ANDROID.md): Android SDK setup
- [RUST_CALLERS.md](RUST_CALLERS.md): cross-repo callers of these crates

---

## Maintainer

Sydney Renee, The Solace Project
GitHub: [@sydneyrenee](https://github.com/sydneyrenee)
Email: [sydney@solace.ofharmony.ai](mailto:sydney@solace.ofharmony.ai)

Issues and pull requests are welcome at
[KotlinMania/rust-lang-kotlin](https://github.com/KotlinMania/rust-lang-kotlin/issues).

---

## License

Dual-licensed under [Apache-2.0](LICENSE-APACHE) or [MIT](LICENSE-MIT), at your option,
matching upstream `rust-lang/rust`. See [COPYRIGHT](COPYRIGHT).
