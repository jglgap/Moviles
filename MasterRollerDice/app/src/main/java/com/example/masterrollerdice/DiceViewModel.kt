package com.example.masterrollerdice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.masterrollerdice.DiceRoll
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class DiceViewModel : ViewModel() {

    // Contadores de dados
    private val _d4Count = MutableLiveData(0)
    val d4Count: LiveData<Int> = _d4Count

    private val _d6Count = MutableLiveData(0)
    val d6Count: LiveData<Int> = _d6Count

    private val _d8Count = MutableLiveData(0)
    val d8Count: LiveData<Int> = _d8Count

    private val _d10Count = MutableLiveData(0)
    val d10Count: LiveData<Int> = _d10Count

    private val _d12Count = MutableLiveData(0)
    val d12Count: LiveData<Int> = _d12Count

    private val _d20Count = MutableLiveData(0)
    val d20Count: LiveData<Int> = _d20Count

    private val _d100Count = MutableLiveData(0)
    val d100Count: LiveData<Int> = _d100Count

    // Resultado de la tirada actual
    private val _rollResult = MutableLiveData<Pair<Int, String>>()
    val rollResult: LiveData<Pair<Int, String>> = _rollResult

    // Historial de tiradas
    private val _rollHistory = MutableLiveData<List<DiceRoll>>(emptyList())
    val rollHistory: LiveData<List<DiceRoll>> = _rollHistory

    // Métodos para incrementar dados
    fun incrementD4() {
        _d4Count.value = (_d4Count.value ?: 0) + 1
    }

    fun decrementD4() {
        val current = _d4Count.value ?: 0
        if (current > 0) _d4Count.value = current - 1
    }

    fun incrementD6() {
        _d6Count.value = (_d6Count.value ?: 0) + 1
    }

    fun decrementD6() {
        val current = _d6Count.value ?: 0
        if (current > 0) _d6Count.value = current - 1
    }

    fun incrementD8() {
        _d8Count.value = (_d8Count.value ?: 0) + 1
    }

    fun decrementD8() {
        val current = _d8Count.value ?: 0
        if (current > 0) _d8Count.value = current - 1
    }

    fun incrementD10() {
        _d10Count.value = (_d10Count.value ?: 0) + 1
    }

    fun decrementD10() {
        val current = _d10Count.value ?: 0
        if (current > 0) _d10Count.value = current - 1
    }

    fun incrementD12() {
        _d12Count.value = (_d12Count.value ?: 0) + 1
    }

    fun decrementD12() {
        val current = _d12Count.value ?: 0
        if (current > 0) _d12Count.value = current - 1
    }

    fun incrementD20() {
        _d20Count.value = (_d20Count.value ?: 0) + 1
    }

    fun decrementD20() {
        val current = _d20Count.value ?: 0
        if (current > 0) _d20Count.value = current - 1
    }

    fun incrementD100() {
        _d100Count.value = (_d100Count.value ?: 0) + 1
    }

    fun decrementD100() {
        val current = _d100Count.value ?: 0
        if (current > 0) _d100Count.value = current - 1
    }

    // Limpiar todos los dados
    fun clearAllDice() {
        _d4Count.value = 0
        _d6Count.value = 0
        _d8Count.value = 0
        _d10Count.value = 0
        _d12Count.value = 0
        _d20Count.value = 0
        _d100Count.value = 0
        _rollResult.value = Pair(0, "")
    }

    // Tirar los dados
    fun rollDice() {
        val d4 = _d4Count.value ?: 0
        val d6 = _d6Count.value ?: 0
        val d8 = _d8Count.value ?: 0
        val d10 = _d10Count.value ?: 0
        val d12 = _d12Count.value ?: 0
        val d20 = _d20Count.value ?: 0
        val d100 = _d100Count.value ?: 0

        val totalDice = d4 + d6 + d8 + d10 + d12 + d20 + d100

        if (totalDice == 0) {
            _rollResult.value = Pair(0, "")
            return
        }

        val results = mutableListOf<Int>()

        // Tirar cada tipo de dado
        repeat(d4) { results.add(Random.nextInt(1, 5)) }
        repeat(d6) { results.add(Random.nextInt(1, 7)) }
        repeat(d8) { results.add(Random.nextInt(1, 9)) }
        repeat(d10) { results.add(Random.nextInt(1, 11)) }
        repeat(d12) { results.add(Random.nextInt(1, 13)) }
        repeat(d20) { results.add(Random.nextInt(1, 21)) }
        repeat(d100) { results.add(Random.nextInt(1, 101)) }

        val total = results.sum()
        val breakdown = results.joinToString(" + ") + " = $total"

        _rollResult.value = Pair(total, breakdown)

        // Guardar en historial
        saveToHistory(results, total)
    }

    private fun saveToHistory(results: List<Int>, total: Int) {
        val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        val diceRoll = DiceRoll(
            date = currentDate,
            d4Count = _d4Count.value ?: 0,
            d6Count = _d6Count.value ?: 0,
            d8Count = _d8Count.value ?: 0,
            d10Count = _d10Count.value ?: 0,
            d12Count = _d12Count.value ?: 0,
            d20Count = _d20Count.value ?: 0,
            d100Count = _d100Count.value ?: 0,
            results = results,
            total = total
        )

        val currentHistory = _rollHistory.value?.toMutableList() ?: mutableListOf()
        currentHistory.add(0, diceRoll) // Añadir al principio
        _rollHistory.value = currentHistory
    }

    // Limpiar historial
    fun clearHistory() {
        _rollHistory.value = emptyList()
    }
}