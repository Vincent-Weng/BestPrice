/**
 * Copyright 2021 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.uwaterloo.pricecompare.util;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Utility class for initializing Firebase services
 */
public class FirebaseUtil {

    private static FirebaseFirestore FIRESTORE;
    private static FirebaseAuth AUTH;
    private static AuthUI AUTH_UI;

    public static FirebaseFirestore getFirestore() {
        if (FIRESTORE == null) {
            FIRESTORE = FirebaseFirestore.getInstance();
        }

        return FIRESTORE;
    }

    public static FirebaseAuth getAuth() {
        if (AUTH == null) {
            AUTH = FirebaseAuth.getInstance();
        }

        return AUTH;
    }

    public static AuthUI getAuthUI() {
        if (AUTH_UI == null) {
            AUTH_UI = AuthUI.getInstance();
        }

        return AUTH_UI;
    }

}
