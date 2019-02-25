package com.he.iamcall.permission

import android.annotation.SuppressLint
import java.lang.reflect.Method

import android.content.Context
import android.telephony.TelephonyManager
import android.util.Log

class TelephonyInfo private constructor() {
    var imeiSIM1: String? = null
        private set
    /*public static void setImeiSIM1(String imeiSIM1) {
    TelephonyInfo.imeiSIM1 = imeiSIM1;
}*/

    var imeiSIM2: String? = null
        private set
    /*public static void setImeiSIM2(String imeiSIM2) {
    TelephonyInfo.imeiSIM2 = imeiSIM2;
}*/

    var isSIM1Ready: Boolean = false
        private set
    /*public static void setSIM1Ready(boolean isSIM1Ready) {
    TelephonyInfo.isSIM1Ready = isSIM1Ready;
}*/

    var isSIM2Ready: Boolean = false
        private set

    /*public static void setSIM2Ready(boolean isSIM2Ready) {
    TelephonyInfo.isSIM2Ready = isSIM2Ready;
}*/

    val isDualSIM: Boolean
        get() = imeiSIM2 != null


    private class GeminiMethodNotFoundException(info: String) : Exception(info) {
        companion object {

            private val serialVersionUID = -996812356902545308L
        }
    }

    companion object {

        private var TelephonyInfo: TelephonyInfo? = null

        @SuppressLint("MissingPermission", "HardwareIds")
        fun getInstance(context: Context): TelephonyInfo? {

            if (TelephonyInfo == null) {

                TelephonyInfo = TelephonyInfo()

                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

                TelephonyInfo!!.imeiSIM1 = telephonyManager.deviceId
                TelephonyInfo!!.imeiSIM2 = null

                try {
                    TelephonyInfo!!.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceIdGemini", 0)
                    TelephonyInfo!!.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceIdGemini", 1)
                } catch (e: GeminiMethodNotFoundException) {
                    e.printStackTrace()

                    try {
                        TelephonyInfo!!.imeiSIM1 = getDeviceIdBySlot(context, "getDeviceId", 0)
                        TelephonyInfo!!.imeiSIM2 = getDeviceIdBySlot(context, "getDeviceId", 1)
                    } catch (e1: GeminiMethodNotFoundException) {
                        //Call here for next manufacturer's predicted method name if you wish
                        e1.printStackTrace()
                    }

                }

                TelephonyInfo!!.isSIM1Ready = telephonyManager.simState == TelephonyManager.SIM_STATE_READY
                TelephonyInfo!!.isSIM2Ready = false

                try {
                    TelephonyInfo!!.isSIM1Ready = getSIMStateBySlot(context, "getSimStateGemini", 0)
                    TelephonyInfo!!.isSIM2Ready = getSIMStateBySlot(context, "getSimStateGemini", 1)
                } catch (e: GeminiMethodNotFoundException) {

                    e.printStackTrace()

                    try {
                        TelephonyInfo!!.isSIM1Ready = getSIMStateBySlot(context, "getSimState", 0)
                        TelephonyInfo!!.isSIM2Ready = getSIMStateBySlot(context, "getSimState", 1)
                    } catch (e1: GeminiMethodNotFoundException) {
                        //Call here for next manufacturer's predicted method name if you wish
                        e1.printStackTrace()
                    }

                }

            }

            return TelephonyInfo
        }

        @SuppressLint("MissingPermission", "HardwareIds")
        @Throws(GeminiMethodNotFoundException::class)
        private fun getDeviceIdBySlot(context: Context, predictedMethodName: String, slotID: Int): String? {

            var imei: String? = null

            val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            try {

                val telephonyClass = Class.forName(telephony.javaClass.name)

                val parameter = arrayOfNulls<Class<*>>(1)
                parameter[0] = Int::class.javaPrimitiveType
                val getSimID = telephonyClass.getMethod(predictedMethodName, *parameter)

                val obParameter = arrayOfNulls<Any>(1)
                obParameter[0] = slotID
                Log.e("Mobile",telephony.line1Number)
                val obPhone = getSimID.invoke(telephony, *obParameter)

                if (obPhone != null) {
                    imei = obPhone.toString()

                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw GeminiMethodNotFoundException(predictedMethodName)
            }

            return imei
        }

        @SuppressLint("MissingPermission", "HardwareIds")
        @Throws(GeminiMethodNotFoundException::class)
        private fun getSIMStateBySlot(context: Context, predictedMethodName: String, slotID: Int): Boolean {

            var isReady = false

            val telephony = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

            try {

                val telephonyClass = Class.forName(telephony.javaClass.name)

                val parameter = arrayOfNulls<Class<*>>(1)
                parameter[0] = Int::class.javaPrimitiveType
                val getSimStateGemini = telephonyClass.getMethod(predictedMethodName, *parameter)
                val obParameter = arrayOfNulls<Any>(1)
                obParameter[0] = slotID
                Log.e("Mobile",telephony.line1Number)
                val obPhone = getSimStateGemini.invoke(telephony, *obParameter)

                if (obPhone != null) {
                    val simState = Integer.parseInt(obPhone.toString())
                    if (simState == TelephonyManager.SIM_STATE_READY) {
                        isReady = true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw GeminiMethodNotFoundException(predictedMethodName)
            }

            return isReady
        }
    }
}