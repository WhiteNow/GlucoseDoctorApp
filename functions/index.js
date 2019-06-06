//const functions = require('firebase-functions');

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });



const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();



//const firestore = new Firestore();
//const settings = {/* Date... */ timestampsInSnapshots: true};
//firestore.settings(settings);

exports.sendNotification = 
functions.firestore.document('Pacientes/{pacientesId}').onWrite((change, context) =>{

const userId = context.params.pacientesId;
//const notificationId = context.params.notificationId;

console.log('The User id is : ', userId);
//console.log('The Notification id is : ', notificationId);

// ref to the parent document

const notificationContent = {
                notification:{
                    title: "/*App name */",
                    body: "You have a new Comment!",
                    icon: "default",
                    click_action: "/*Package */_TARGET_NOTIFICATION"
            }
        };

        return admin.messaging().sendToDevice("dUxFlDN7KkA:APA91bECnDpmBN-HT4Sa-Xaj6fE-nq1yN8d86qFEBpIQ1ia_oEl-H5WjaZWQtmqqVEIGCZtVg2agnwhOb5Q__O0Mb_ve-SrNDCQTEmMcGckKGSD__iSf9R_N761nr7VF0SKHgsSpWDA7",
		notificationContent).then(result => {
            return console.log("Notification sent!");
            //admin.firestore().collection("notifications").doc(userEmail).collection("userNotifications").doc(notificationId).delete();
        
	
    //const toUser = admin.firestore().collection("Users").doc(userId).collection("Notifications").doc(notificationId).get();

        });

   });
