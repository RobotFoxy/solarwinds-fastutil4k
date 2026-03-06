@file:Suppress("unused", "NOTHING_TO_INLINE")
@file:JvmName("java8-function-invoke")

package net.ccbluex.fastutil

import java.util.function.BiConsumer
import java.util.function.BiFunction
import java.util.function.BiPredicate
import java.util.function.BinaryOperator
import java.util.function.BooleanSupplier
import java.util.function.Consumer
import java.util.function.DoubleBinaryOperator
import java.util.function.DoubleConsumer
import java.util.function.DoubleFunction
import java.util.function.DoublePredicate
import java.util.function.DoubleSupplier
import java.util.function.DoubleToIntFunction
import java.util.function.DoubleToLongFunction
import java.util.function.DoubleUnaryOperator
import java.util.function.Function
import java.util.function.IntBinaryOperator
import java.util.function.IntConsumer
import java.util.function.IntFunction
import java.util.function.IntPredicate
import java.util.function.IntSupplier
import java.util.function.IntToDoubleFunction
import java.util.function.IntToLongFunction
import java.util.function.IntUnaryOperator
import java.util.function.LongBinaryOperator
import java.util.function.LongConsumer
import java.util.function.LongFunction
import java.util.function.LongPredicate
import java.util.function.LongSupplier
import java.util.function.LongToDoubleFunction
import java.util.function.LongToIntFunction
import java.util.function.LongUnaryOperator
import java.util.function.ObjDoubleConsumer
import java.util.function.ObjIntConsumer
import java.util.function.ObjLongConsumer
import java.util.function.Predicate
import java.util.function.Supplier
import java.util.function.ToDoubleBiFunction
import java.util.function.ToDoubleFunction
import java.util.function.ToIntBiFunction
import java.util.function.ToIntFunction
import java.util.function.ToLongBiFunction
import java.util.function.ToLongFunction
import java.util.function.UnaryOperator

inline operator fun <T, U> BiConsumer<T, U>.invoke(t: T, u: U) = accept(t, u)
inline operator fun <T, U, R> BiFunction<T, U, R>.invoke(t: T, u: U): R = apply(t, u)
inline operator fun <T, U> BiPredicate<T, U>.invoke(t: T, u: U): Boolean = test(t, u)
inline operator fun <T> BinaryOperator<T>.invoke(t: T, u: T): T = apply(t, u)
inline operator fun BooleanSupplier.invoke(): Boolean = asBoolean
inline operator fun <T> Consumer<T>.invoke(t: T) = accept(t)
inline operator fun DoubleBinaryOperator.invoke(left: Double, right: Double): Double = applyAsDouble(left, right)
inline operator fun DoubleConsumer.invoke(value: Double) = accept(value)
inline operator fun <R> DoubleFunction<R>.invoke(value: Double): R = apply(value)
inline operator fun DoublePredicate.invoke(value: Double): Boolean = test(value)
inline operator fun DoubleSupplier.invoke(): Double = asDouble
inline operator fun DoubleToIntFunction.invoke(value: Double): Int = applyAsInt(value)
inline operator fun DoubleToLongFunction.invoke(value: Double): Long = applyAsLong(value)
inline operator fun DoubleUnaryOperator.invoke(value: Double): Double = applyAsDouble(value)
inline operator fun <T, R> Function<T, R>.invoke(t: T): R = apply(t)
inline operator fun IntBinaryOperator.invoke(left: Int, right: Int): Int = applyAsInt(left, right)
inline operator fun IntConsumer.invoke(value: Int) = accept(value)
inline operator fun <R> IntFunction<R>.invoke(value: Int): R = apply(value)
inline operator fun IntPredicate.invoke(value: Int): Boolean = test(value)
inline operator fun IntSupplier.invoke(): Int = asInt
inline operator fun IntToDoubleFunction.invoke(value: Int): Double = applyAsDouble(value)
inline operator fun IntToLongFunction.invoke(value: Int): Long = applyAsLong(value)
inline operator fun IntUnaryOperator.invoke(value: Int): Int = applyAsInt(value)
inline operator fun LongBinaryOperator.invoke(left: Long, right: Long): Long = applyAsLong(left, right)
inline operator fun LongConsumer.invoke(value: Long) = accept(value)
inline operator fun <R> LongFunction<R>.invoke(value: Long): R = apply(value)
inline operator fun LongPredicate.invoke(value: Long): Boolean = test(value)
inline operator fun LongSupplier.invoke(): Long = asLong
inline operator fun LongToDoubleFunction.invoke(value: Long): Double = applyAsDouble(value)
inline operator fun LongToIntFunction.invoke(value: Long): Int = applyAsInt(value)
inline operator fun LongUnaryOperator.invoke(value: Long): Long = applyAsLong(value)
inline operator fun <T> ObjDoubleConsumer<T>.invoke(t: T, value: Double) = accept(t, value)
inline operator fun <T> ObjIntConsumer<T>.invoke(t: T, value: Int) = accept(t, value)
inline operator fun <T> ObjLongConsumer<T>.invoke(t: T, value: Long) = accept(t, value)
inline operator fun <T> Predicate<T>.invoke(t: T): Boolean = test(t)
inline operator fun <T> Supplier<T>.invoke(): T = get()
inline operator fun <T, U> ToDoubleBiFunction<T, U>.invoke(t: T, u: U): Double = applyAsDouble(t, u)
inline operator fun <T> ToDoubleFunction<T>.invoke(t: T): Double = applyAsDouble(t)
inline operator fun <T, U> ToIntBiFunction<T, U>.invoke(t: T, u: U): Int = applyAsInt(t, u)
inline operator fun <T> ToIntFunction<T>.invoke(t: T): Int = applyAsInt(t)
inline operator fun <T, U> ToLongBiFunction<T, U>.invoke(t: T, u: U): Long = applyAsLong(t, u)
inline operator fun <T> ToLongFunction<T>.invoke(t: T): Long = applyAsLong(t)
inline operator fun <T> UnaryOperator<T>.invoke(t: T): T = apply(t)
