# PermissionHelper
A library made to simplify Permission check and requests for Android M and above.

## Features:
* Request for permission(s) and obtain the result easily.

## Usage:
* Make a `PermissionHelper` object.

* For multiple permissions, simply add them on the builder or the `PermissionHelper` object:

* To listen to the permission result, override your `Activity`'s `#onRequestPermissionsResult()` and call the result on the `PermissionHelper` object:

* By default, this library generates a random integer (between 1-100) as the request code. This can be overridden by specifying it before requesting for permission.