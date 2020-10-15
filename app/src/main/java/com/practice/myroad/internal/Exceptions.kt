package com.practice.myroad.internal

import java.io.IOException

class NoConnectivityException(message: String):IOException(message)

class NonExistentRoadException(message: String):IOException(message)