package com.getcard.hub.providers.example

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Build
import android.util.Log

object BluetoothHelper {

    private const val TAG = "BluetoothHelper"

    data class BtDeviceInfo(
        val name: String,
        val address: String,
    )

    // Definir as permissões necessárias baseado na versão do Android
    val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
        )
    }

    /**
     * Obtém a lista de dispositivos Bluetooth pareados
     */
    fun getPairedDevices(context: Context): List<BtDeviceInfo> {
        try {
            val btManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
            val btAdapter = btManager?.adapter

            if (btAdapter == null) {
                Log.w(TAG, "BluetoothAdapter não disponível")
                return emptyList()
            }

            if (!btAdapter.isEnabled) {
                Log.w(TAG, "Bluetooth está desativado")
                return emptyList()
            }

            @SuppressLint("MissingPermission")
            val pairedDevices: Set<BluetoothDevice> = btAdapter.bondedDevices ?: emptySet()

            return pairedDevices.mapNotNull { device ->
                try {
                    BtDeviceInfo(device.name ?: "Dispositivo Desconhecido", device.address)
                } catch (e: SecurityException) {
                    Log.e(TAG, "Erro ao acessar informações do dispositivo: ${e.message}")
                    null
                }
            }.sortedBy { it.name }
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao obter dispositivos pareados", e)
            return emptyList()
        }
    }
}