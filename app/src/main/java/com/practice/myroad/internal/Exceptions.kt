package com.practice.myroad.internal

import java.io.IOException

class NoConnectivityException:IOException()

class NonExistentRoadException(message: String):IOException(message)