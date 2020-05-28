LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Constant
LOCAL_SRC_FILES := Constant.c

include $(BUILD_SHARED_LIBRARY)