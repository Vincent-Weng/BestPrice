const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.addToStoreRec = functions.firestore.document("/items/{itemID}")
    .onCreate((snap, context) => {
      const storeId = snap.data().storeId;
      const itemRef = snap.ref;
      const storeDocRef = admin.firestore().collection("stores").doc(storeId);
      storeDocRef.update({
        recommendations: admin.firestore.FieldValue.arrayUnion(itemRef),
      });
      functions.logger.log("Item added to store recommendations", snap.id);
      return null;
    });

exports.addToCategoryRec = functions.firestore.document("/items/{itemID}")
    .onCreate(async (snap, context) => {
      const upc = snap.data().upc;
      const itemRef = snap.ref;
      const productDocRef = admin.firestore().collection("products").doc(upc);
      const doc = await productDocRef.get();
      if (!doc.exists) {
        functions.logger.log("No such product:", upc);
      } else {
        const categoryId = doc.data().categoryId;
        const categoryDocRef = admin.firestore()
            .collection("categories").doc(categoryId);
        categoryDocRef.update({
          recommendations: admin.firestore.FieldValue.arrayUnion(itemRef),
        });
      }
      functions.logger.log("Item added to category recommendations", snap.id);
    });

exports.notifyUser = functions.firestore.document("/items/{itemID}")
    .onCreate(async (snap, context) => {
      const upc = snap.data().upc;
      const productDocRef = admin.firestore().collection("products").doc(upc);
      const prod = await productDocRef.get();
      if (!prod.exists) {
        functions.logger.log("No such product:", upc);
      } else {
        const viewedByUsers = prod.data().viewedBy;
        functions.logger.log("Notifying users: ", viewedByUsers);
        for (const user of viewedByUsers) {
          const userDocRef = admin.firestore().collection("users").doc(user);
          const u = await userDocRef.get();
          if (!u.exists) {
            functions.logger.log("No such user:", user);
          } else {
            const msgToken = u.data().messageToken;
            const message = {
              notification: {
                title:
                "New price!",
                body:
                `Another user added a new price for ${prod.data().name}. ` +
                "Check it out!",
              },
            };
            admin.messaging().sendToDevice(msgToken, message);
            functions.logger.log("Message sent to user " +
            user + " about " + upc);
          }
        }
      }
    });

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//   functions.logger.info("Hello logs!", {structuredData: true});
//   response.send("Hello from Firebase!");
// });

