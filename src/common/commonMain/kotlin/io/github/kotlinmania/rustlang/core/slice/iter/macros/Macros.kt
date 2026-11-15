// port-lint: source slice/iter/macros.rs
package io.github.kotlinmania.rustlang.core.slice.iter.macros

// Macros used by iterators of slice.

private const val IF_ZST_TEMPLATE: String = "{ifBranch T.IsZst {val \$len= unsafe {& var*(& raw var \$thisRef.endOrLen).cast.< usize>()}; \$zstBody} elseBranch {val \$end= unsafe {& var*(& raw var \$thisRef.endOrLen).cast.< NonNull< T>>()}; \$otherBody}}"

private const val IF_ZST_TEMPLATE_1: String = "{ifBranch T.IsZst {val \$len= \$thisRef.endOrLen.addr(); \$zstBody} elseBranch {val \$end= unsafe {mem.transmute.<* const T, NonNull< T>>(\$thisRef.endOrLen)}; \$otherBody}}"

private const val IS_EMPTY_TEMPLATE: String = "ifZst(\$thisRef, len=> len== 0, end=> \$thisRef.ptr== end, )"

private const val LEN_TEMPLATE: String = "{ifZst(\$thisRef, len=> len, end=> {unsafe {end.offsetFromUnsigned(\$thisRef.ptr)}}, )}"

private const val ITERATOR_TEMPLATE: String = "class<, T> \$name<, T> {unsafe fun nextBackUnchecked(& var this)-> \$elem {unsafe {this.preDecEnd(1). \$intoRef()}} fun makeSlice(& this)->&[T] {unsafe {fromRawParts(this.ptr.asPtr(), len(this))}} unsafe fun postIncStart(& var this, offset: usize)-> NonNull< T> {val old= this.ptr; unsafe {ifZst(var this, len=>* len= crate.intrinsics.uncheckedSub(* len, offset), end=> this.ptr= this.ptr.add(offset), ); } old} unsafe fun preDecEnd(& var this, offset: usize)-> NonNull< T> {ifZst(var this, len=> unsafe {* len= crate.intrinsics.uncheckedSub(* len, offset); this.ptr}, end=> unsafe {* end= end.sub(offset); * end}, )}} class< T> ExactSizeIterator forLoop \$name<, T> {fun len(& this)-> usize {len(this)} fun isEmpty(& this)-> bool {isEmpty(this)}} class<, T> Iterator forLoop \$name<, T> {type Item= \$elem; fun next(& var this)-> Option< \$elem> {val ptr= this.ptr; val endOrLen= this.endOrLen; unsafe {ifBranch T.IsZst {val len= endOrLen.addr(); ifBranch len== 0 {ret None; } this.endOrLen= withoutProvenanceMut(len.uncheckedSub(1)); } elseBranch {ifBranch ptr== crate.intrinsics.transmute.< \$ptr, NonNull< T>>(endOrLen) {ret None; } this.ptr= ptr.add(1); } Some( {ptr}. \$intoRef())}} fun nextChunk< const N: usize>(& var this)-> Result<[\$elem; N], crate.array.IntoIter< \$elem, N>> {ifBranch T.IsZst|| len(this)< N {ret crate.array.iterNextChunk(this); } unsafe {val r= this.postIncStart(N).castArray(). \$intoRef(). \$arrayRef(); Ok(r)}} fun sizeHint(& this)->(usize, Option< usize>) {val exact= len(this); (exact, Some(exact))} fun count(this)-> usize {len(this)} fun nth(& var this, n: usize)-> Option< \$elem> {ifBranch n>= len(this) {ifZst(var this, len=>* len= 0, end=> this.ptr=* end, ); ret None; } unsafe {this.postIncStart(n); Some(this.postIncStart(1). \$intoRef())}} fun advanceBy(& var this, n: usize)-> Result<(), NonZero< usize>> {val advance= cmp.min(len(this), n); unsafe {this.postIncStart(advance)}; NonZero.new(n- advance).mapOr(Ok(()), Err)} fun last(var this)-> Option< \$elem> {this.nextBack()} fun fold< B, F>(this, init: B, var f: F)-> B where F: FnMut(B, Self.Item)-> B, {ifBranch isEmpty(this) {ret init; } val var acc= init; val var i= 0; val len= len(this); loop {acc= f(acc, unsafe {\$(\$mut)?* this.ptr.add(i).asPtr()}); i= unsafe {i.uncheckedAdd(1)}; ifBranch i== len {breakTarget; }} acc} fun forEach< F>(var this, var f: F) where Self: Sized, F: FnMut(Self.Item), {whileLoop val Some(x)= this.next() {f(x); }} fun all< F>(& var this, var f: F)-> bool where Self: Sized, F: FnMut(Self.Item)-> bool, {whileLoop val Some(x)= this.next() {ifBranch f(x) {ret falseVal; }} trueVal} fun any< F>(& var this, var f: F)-> bool where Self: Sized, F: FnMut(Self.Item)-> bool, {whileLoop val Some(x)= this.next() {ifBranch f(x) {ret trueVal; }} falseVal} fun find< P>(& var this, var predicate: P)-> Option< Self.Item> where Self: Sized, P: FnMut(& Self.Item)-> bool, {whileLoop val Some(x)= this.next() {ifBranch predicate(& x) {ret Some(x); }} None} fun findMap< B, F>(& var this, var f: F)-> Option< B> where Self: Sized, F: FnMut(Self.Item)-> Option< B>, {whileLoop val Some(x)= this.next() {ifBranch val Some(y)= f(x) {ret Some(y); }} None} fun position< P>(& var this, var predicate: P)-> Option< usize> where Self: Sized, P: FnMut(Self.Item)-> bool, {val n= len(this); val var i= 0; whileLoop val Some(x)= this.next() {ifBranch predicate(x) {unsafe {assertUnchecked(i< n)}; ret Some(i); } i+= 1; } None} fun rposition< P>(& var this, var predicate: P)-> Option< usize> where P: FnMut(Self.Item)-> bool, Self: Sized+ ExactSizeIterator+ DoubleEndedIterator {val n= len(this); val var i= n; whileLoop val Some(x)= this.nextBack() {i-= 1; ifBranch predicate(x) {unsafe {assertUnchecked(i< n)}; ret Some(i); }} None} unsafe fun iteratorGetUnchecked(& var this, idx: usize)-> Self.Item {unsafe {\$(\$mut)?* this.ptr.asPtr().add(idx)}}\$(\$extra)*} class<, T> DoubleEndedIterator forLoop \$name<, T> {fun nextBack(& var this)-> Option< \$elem> {unsafe {ifBranch isEmpty(this) {None} elseBranch {Some(this.nextBackUnchecked())}}} fun nthBack(& var this, n: usize)-> Option< \$elem> {ifBranch n>= len(this) {ifZst(var this, len=>* len= 0, end=>* end= this.ptr, ); ret None; } unsafe {this.preDecEnd(n); Some(this.nextBackUnchecked())}} fun advanceBackBy(& var this, n: usize)-> Result<(), NonZero< usize>> {val advance= cmp.min(len(this), n); unsafe {this.preDecEnd(advance)}; NonZero.new(n- advance).mapOr(Ok(()), Err)}} class< T> FusedIterator forLoop \$name<, T> {} unsafe class< T> TrustedLen forLoop \$name<, T> {} class< T> Default forLoop \$name<, T> {fun default()-> Self {(\$(\$mut)?[]).intoIter()}}"

private const val FORWARD_ITERATOR_TEMPLATE: String = "class<, \$elem, P> Iterator forLoop \$name<, \$elem, P> where P: FnMut(& T)-> bool, {type Item= \$iterOf; fun next(& var this)-> Option< \$iterOf> {this.inner.next()} fun sizeHint(& this)->(usize, Option< usize>) {this.inner.sizeHint()}} class<, \$elem, P> FusedIterator forLoop \$name<, \$elem, P> where P: FnMut(& T)-> bool {}"

/**
 * Convenience & performance macro for consuming the `endOrLen` field, by
 * giving a `() Int` or `() NonNull<T>` depending whether `T` is
 * or is not a ZST respectively.
 *
 * Internally, this reads the `end` through a pointer-to-`NonNull` so that
 * it'll get the appropriate non-null metadata in the backend without needing
 * to call `assume` manually.
 */

internal fun ifZst(thisRef: String = "", len: String = "", zstBody: String = "", end: String = "", otherBody: String = "", clause: String = ""): String =
    when {
        clause == "var = > = >" && thisRef.isNotEmpty() && len.isNotEmpty() && zstBody.isNotEmpty() && end.isNotEmpty() && otherBody.isNotEmpty() -> arrayOf("\$thisRef" to thisRef, "\$len" to len, "\$zstBody" to zstBody, "\$end" to end, "\$otherBody" to otherBody).fold(IF_ZST_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        clause == "= > = >" && thisRef.isNotEmpty() && len.isNotEmpty() && zstBody.isNotEmpty() && end.isNotEmpty() && otherBody.isNotEmpty() -> arrayOf("\$thisRef" to thisRef, "\$len" to len, "\$zstBody" to zstBody, "\$end" to end, "\$otherBody" to otherBody).fold(IF_ZST_TEMPLATE_1) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
        else -> ""
    }

internal fun isEmpty(thisRef: String = ""): String =
    IS_EMPTY_TEMPLATE.replace("\$thisRef", thisRef)

internal fun len(thisRef: String = ""): String =
    LEN_TEMPLATE.replace("\$thisRef", thisRef)

internal fun iterator(name: String = "", ptr: String = "", elem: String = "", rawMut: String = "", mut: String = "", intoRef: String = "", arrayRef: String = "", extra: String = ""): String =
    if (rawMut.isEmpty()) {
        arrayOf("\$name" to name, "\$ptr" to ptr, "\$elem" to elem, "\$mut" to mut, "\$intoRef" to intoRef, "\$arrayRef" to arrayRef, "\$extra" to extra).fold(ITERATOR_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    } else {
        arrayOf("\$name" to name, "\$ptr" to ptr, "\$elem" to elem, "\$mut" to mut, "\$intoRef" to intoRef, "\$arrayRef" to arrayRef, "\$extra" to extra).fold(ITERATOR_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
    }

internal fun forwardIterator(name: String = "", elem: String = "", iterOf: String = ""): String =
    arrayOf("\$name" to name, "\$elem" to elem, "\$iterOf" to iterOf).fold(FORWARD_ITERATOR_TEMPLATE) { acc0, (k0, v0) -> acc0.replace(k0, v0) }
