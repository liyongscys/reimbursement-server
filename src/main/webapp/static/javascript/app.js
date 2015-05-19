angular.module("reimbursement.templates",["cropping/cropping.tpl.html","csrfTestingPage/csrfTestingPage.tpl.html","dashboard/dashboard.tpl.html","login/login.tpl.html","signature/signature-pad.directive.tpl.html","signature/signature-qr-error.tpl.html","signature/signature-qr.tpl.html","signature/signature.tpl.html","spinner/spinner.directive.tpl.html"]),angular.module("cropping/cropping.tpl.html",[]).run(["$templateCache",function(a){a.put("cropping/cropping.tpl.html",'<div id="signatureCrop"> <h1 class="page-header"> <i class="fa fa-crop"></i> {{ \'reimbursement.cropSignature.title\' | translate }} </h1> <div class="page"> <spinner id="spinnerCroppingSubmit" label="{{ \'reimbursement.loading.label\' | translate }}"></spinner> <div> <img crop id="croppingImage" dimensions="dimensions" ng-src="{{ imageUri }}"> </div> <div class="buttons"> <button class="btn btn-primary" ng-click="submit()" ng-disabled="!hasDimensions"> <i class="fa fa-check"></i> {{ \'reimbursement.cropSignature.submit\' | translate }} </button> </div> </div> </div>')}]),angular.module("csrfTestingPage/csrfTestingPage.tpl.html",[]).run(["$templateCache",function(a){a.put("csrfTestingPage/csrfTestingPage.tpl.html",'<div id="login"> <h1 class="page-header"> <i class="fa fa-sign-in"></i> CSRF Testing Page </h1> <h3>Login</h3> <div class="alert alert-success" ng-show="loginSent && loginSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="loginSent && !loginSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <label for="inputUsername" class="col-sm-4 control-label">{{ \'reimbursement.login.usernameLabel\' | translate }}</label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputUsername" ng-model="form.username" placeholder="{{ \'reimbursement.login.usernameLabel\' | translate }}"> </div> </div> <div class="form-group"> <label for="inputPassword" class="col-sm-4 control-label">{{ \'reimbursement.login.passwordLabel\' | translate }}</label> <div class="col-sm-5"> <input type="password" class="form-control" id="inputPassword" ng-model="form.password" placeholder="{{ \'reimbursement.login.passwordLabel\' | translate }}"> </div> </div> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="submit()"> {{ \'reimbursement.login.submitLabel\' | translate }} </button> </div> </div> </form> <h3>Public getUsers</h3> <div class="alert alert-success" ng-show="getUsersSent && getUsersSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="getUsersSent && !getUsersSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="getUsers()"> get Users </button> </div> </div> </form> <h3>Private getUsers</h3> <div class="alert alert-success" ng-show="getPrivateUsersSent && getPrivateUsersSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="getPrivateUsersSent && !getPrivateUsersSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="getPrivateUsers()"> get Users </button> </div> </div> </form> <h3>Public sendString</h3> <div class="alert alert-success" ng-show="sendStringSent && sendStringSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="sendStringSent && !sendStringSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <label for="inputString" class="col-sm-4 control-label">String: </label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputString" ng-model="stringForm.string" placeholder="some random string"> </div> </div> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="sendString()"> send String </button> </div> </div> </form> <h3>Public send CroppingDto</h3> <div class="alert alert-success" ng-show="sendCroppingDtoSent && sendCroppingDtoSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="sendCroppingDtoSent && !sendCroppingDtoSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <label for="inputCroppingDtoHeight" class="col-sm-4 control-label">Height: </label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputCroppingDtoHeight" ng-model="croppingDtoForm.height" placeholder="some random string"> </div> </div> <div class="form-group"> <label for="inputCroppingDtoWidth" class="col-sm-4 control-label">Width: </label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputCroppingDtoWidth" ng-model="croppingDtoForm.width" placeholder="some random string"> </div> </div> <div class="form-group"> <label for="inputCroppingDtoTop" class="col-sm-4 control-label">Top: </label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputCroppingDtoTop" ng-model="croppingDtoForm.top" placeholder="some random string"> </div> </div> <div class="form-group"> <label for="inputCroppingDtoLeft" class="col-sm-4 control-label">Left: </label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputCroppingDtoLeft" ng-model="croppingDtoForm.left" placeholder="some random string"> </div> </div> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="sendCroppingDto()"> send String </button> </div> </div> </form> </div>')}]),angular.module("dashboard/dashboard.tpl.html",[]).run(["$templateCache",function(a){a.put("dashboard/dashboard.tpl.html",'<div id="dashboard"> <h1 class="page-header">{{ \'reimbursement.dashboard.title\' | translate }}</h1> {{ dashboard }} </div>')}]),angular.module("login/login.tpl.html",[]).run(["$templateCache",function(a){a.put("login/login.tpl.html",'<div id="login"> <h1 class="page-header"> <i class="fa fa-sign-in"></i> {{ \'reimbursement.login.title\' | translate }} </h1> <div class="alert alert-success" ng-show="loginSent && loginSuccess"><i class="fa fa-thumbs-o-up"></i> Very good Sir!</div> <div class="alert alert-danger" ng-show="loginSent && !loginSuccess"><i class="fa fa-thumbs-o-down"></i> Not good, duckhead...</div> <form class="form-horizontal"> <div class="form-group"> <label for="inputUsername" class="col-sm-4 control-label">{{ \'reimbursement.login.usernameLabel\' | translate }}</label> <div class="col-sm-5"> <input type="text" class="form-control" id="inputUsername" ng-model="form.username" placeholder="{{ \'reimbursement.login.usernameLabel\' | translate }}"> </div> </div> <div class="form-group"> <label for="inputPassword" class="col-sm-4 control-label">{{ \'reimbursement.login.passwordLabel\' | translate }}</label> <div class="col-sm-5"> <input type="password" class="form-control" id="inputPassword" ng-model="form.password" placeholder="{{ \'reimbursement.login.passwordLabel\' | translate }}"> </div> </div> <div class="form-group"> <div class="col-sm-9"> <button class="btn btn-primary pull-right" ng-click="submit()"> {{ \'reimbursement.login.submitLabel\' | translate }} </button> </div> </div> </form> </div>')}]),angular.module("signature/signature-pad.directive.tpl.html",[]).run(["$templateCache",function(a){a.put("signature/signature-pad.directive.tpl.html",'<div class="signaturePad"> <div class="pad"> <canvas ng-style="{width: signatureWidth, height: signatureHeight}"></canvas> </div> <div class="buttons"> <button class="btn btn-default" ng-click="clearSignature()"> <i class="fa fa-ban"></i> {{ "reimbursement.captureSignature.touch.reset" | translate }} </button> <button class="btn btn-primary" ng-click="submitSignature()"> {{ "reimbursement.captureSignature.nextStep" | translate }} </button> </div> </div>')}]),angular.module("signature/signature-qr-error.tpl.html",[]).run(["$templateCache",function(a){a.put("signature/signature-qr-error.tpl.html",'<div id="signatureCaptureQRError"> <div class="modal-header"> <div class="close" ng-click="dismiss()"></div> <h3 class="modal-title"> <i class="fa fa-warning"></i> {{ \'reimbursement.captureSignature.qr.error.title\' | translate }} </h3> </div> <div class="modal-body"> {{ \'reimbursement.captureSignature.qr.error.message\' | translate }} </div> <div class="modal-footer"> <button class="btn btn-primary" ng-click="dismiss()"> {{ \'reimbursement.captureSignature.qr.error.okayButton\' | translate }} </button> </div> </div>')}]),angular.module("signature/signature-qr.tpl.html",[]).run(["$templateCache",function(a){a.put("signature/signature-qr.tpl.html",'<div id="signatureCaptureQR"> <spinner id="spinnerSignatureQR" label="{{ \'reimbursement.loading.label\' | translate }}"></spinner> <div class="modal-header"> <a class="close" ng-click="dismiss()">&times;</a> <h3 class="modal-title">{{ \'reimbursement.captureSignature.qr.title\' | translate }}</h3> </div> <div class="modal-body"> {{ \'reimbursement.captureSignature.qr.info\' | translate }} <div class="qrCodeWrapper"> <div class="qrUrl">{{ qrUrl }}</div> <div class="or">{{ \'reimbursement.captureSignature.qr.or\' | translate }}</div> <qrcode data="{{ qrUrl }}" size="300"></qrcode> </div> </div> <div class="modal-footer"> <button class="btn btn-default" ng-click="dismiss()"> <i class="fa fa-times"></i> {{ \'reimbursement.captureSignature.qr.cancel\' | translate }} </button> <button class="btn btn-primary" ng-click="checkAndClose()"> <i class="fa fa-check"></i> {{ \'reimbursement.captureSignature.qr.capturedOnMobile\' | translate }} </button> </div> </div>')}]),angular.module("signature/signature.tpl.html",[]).run(["$templateCache",function(a){a.put("signature/signature.tpl.html",'<div id="signatureCapture"> <h1 class="page-header"><i class="fa fa-pencil-square-o"></i> {{ \'reimbursement.captureSignature.title\' | translate }}</h1> <div class="well">{{ \'reimbursement.captureSignature.info\' | translate }}</div> <div class="col-lg-8 col-lg-offset-2"> <ul class="nav nav-tabs"> <li role="captureMethod" ng-class="{active: showTouchInput}"> <a href ng-click="selectTouchTab()">{{ \'reimbursement.captureSignature.touch.tab\' | translate }}</a> </li> <li role="captureMethod" ng-class="{active: showUploadImage}"> <a href ng-click="selectUploadTab()">{{ \'reimbursement.captureSignature.upload.tab\' | translate }}</a> </li> </ul> <form id="signatureCaptureImage" ng-show="showUploadImage"> <spinner id="spinnerSignatureImage" label="{{ \'reimbursement.captureSignature.spinnerUploading\' | translate }}"></spinner> <div flow-init="{\n				target: postSignaturePath,\n				testChunks: false,\n				singleFile: true,\n				simultaneousUploads: 1\n			}" flow-name="flow.image" flow-files-submitted="$flow.upload()" flow-upload-started="showSpinner(\'spinnerSignatureImage\')" flow-complete="getImageAndGoToNextPage()"> <div class="image-upload"> <div flow-drop flow-btn ng-class="dropClass" flow-drag-enter="dropClass=\'dragOver\'" flow-drag-leave="dropClass=\'\'"> <div class="center uploadButton"> <i class="fa fa-plus-circle"></i> {{ \'reimbursement.captureSignature.upload.centerText\' | translate }} </div> </div> </div> </div> </form> <form id="signatureCaptureTouch" ng-show="showTouchInput"> <spinner id="spinnerSignatureTouch" label="{{ \'reimbursement.captureSignature.spinnerUploading\' | translate }}"></spinner> <div ng-show="!Modernizr.touch && !forceSignaturePad"> <div class="alert alert-warning"> {{ \'reimbursement.captureSignature.touch.warning\' | translate }} <div class="buttons"> <button class="btn btn-warning" ng-click="forceSignaturePad = true"> <i class="fa fa-exclamation-circle"></i> {{ \'reimbursement.captureSignature.touch.tryAnyway\' | translate }} </button> <button class="btn btn-primary" ng-click="showQR()"> <i class="fa fa-tablet"></i> {{ \'reimbursement.captureSignature.touch.tryMobile\' | translate }} </button> </div> </div> </div> <div ng-show="Modernizr.touch || forceSignaturePad"> <div class="resetForceSignature" ng-show="forceSignaturePad" ng-click="forceSignaturePad = false"> <i class="fa fa-mobile"></i> </div> <signature-pad submit="submitTouch"></signature-pad> <div flow-init="{\n					target: postSignaturePath,\n					testChunks: false,\n					singleFile: true,\n					simultaneousUploads: 1\n				}" flow-name="flow.touch" flow-upload-started="showSpinner(\'spinnerSignatureTouch\')" flow-complete="getImageAndGoToNextPage()"> </div> </div> </form> </div> </div>')}]),angular.module("spinner/spinner.directive.tpl.html",[]).run(["$templateCache",function(a){a.put("spinner/spinner.directive.tpl.html",'<div class="spinner" ng-class="{showSpinner: showSpinner}"> <div class="whiteBackground"></div> <div class="spin"> <div class="rect rect1"></div> <div class="rect rect2"></div> <div class="rect rect3"></div> <div class="rect rect4"></div> <div class="rect rect5"></div> <div class="textLabel" ng-hide="label === \'\'"> {{ label }} </div> </div> </div>')}]),function(){"use strict";deferredBootstrapper.bootstrap({element:document.body,module:"reimbursement",resolve:{LANGUAGES:["$http",function(a){return a.get("./languages/languages.json")}]}})}();var app=angular.module("reimbursement",["reimbursement.templates","ui.router","ui.bootstrap","pascalprecht.translate","monospaced.qrcode","flow"]);app.constant("Modernizr",Modernizr),app.config(["$stateProvider","$urlRouterProvider","$translateProvider","$locationProvider","LANGUAGES",function(a,b,c,d,e){"use strict";for(var f in e)e.hasOwnProperty(f)&&c.translations(f,e[f]);c.preferredLanguage("en"),a.state("signature",{url:"/signature",templateUrl:"signature/signature.tpl.html",controller:"SignatureController"}).state("cropping",{url:"/cropping",params:{imageUri:null},templateUrl:"cropping/cropping.tpl.html",controller:"CroppingController"}).state("dashboard",{url:"/dashboard",templateUrl:"dashboard/dashboard.tpl.html",controller:"DashboardController"}).state("login",{url:"/login",templateUrl:"login/login.tpl.html",controller:"LoginController"}).state("csrfTestingPage",{url:"/csrfTestingPage",templateUrl:"csrfTestingPage/csrfTestingPage.tpl.html",controller:"CsrfTestingPageController"}),b.otherwise("/signature"),d.hashPrefix("!")}]),app.factory("base64BinaryConverterService",[function(){"use strict";return{toBase64:function(a,b){var c=new window.FileReader;c.onload=function(){b(c.result)},c.readAsDataURL(a)},toBinary:function(a){for(var b=a.split(",")[0].split(":")[1].split(";base64")[0],c=b.split("image/")[1],d=window.atob(a.split(",")[1]),e=d.length,f=new window.ArrayBuffer(e),g=new window.Uint8Array(f),h=0;e>h;h++)g[h]=d.charCodeAt(h);var i=new window.Blob([g],{type:b});i.lastModifiedDate=new Date,i.name=(new Date).toUTCString()+"."+c;var j=i;return j}}}]),app.factory("loginRestService",["$http",function(a){"use strict";return{postLogin:function(b){return a({method:"POST",url:"http://localhost:8080/api/login",data:jQuery.param(b),headers:{"Content-Type":"application/x-www-form-urlencoded"}})}}}]),app.controller("LoginController",["$scope","loginRestService",function(a,b){"use strict";a.form={username:null,password:null},a.loginSent=!1,a.loginSuccess=!1,a.submit=function(){b.postLogin(a.form).then(function(){a.loginSuccess=!0},function(){a.loginSuccess=!1})["finally"](function(){a.loginSent=!0})}}]),app.factory("csrfTestingPageRestService",["$http",function(a){"use strict";return{postLogin:function(b){return a({method:"POST",url:"http://localhost:8080/api/login",data:jQuery.param(b),headers:{"Content-Type":"application/x-www-form-urlencoded"}})},getUsers:function(){return a({method:"GET",url:"http://localhost:8080/testingpublic"})},getPrivateUsers:function(){return a({method:"GET",url:"http://localhost:8080/testingprivate"})},sendString:function(b){return a({method:"POST",url:"http://localhost:8080/testingpublic/string?"+jQuery.param(b)})},sendCroppingDto:function(b){return a({method:"POST",url:"http://localhost:8080/testingpublic/croppingdto",data:b,headers:{"Content-Type":"application/json"}})}}}]),app.controller("CsrfTestingPageController",["$scope","csrfTestingPageRestService",function(a,b){"use strict";a.form={username:null,password:null},a.stringForm={string:null},a.croppingDtoForm={width:null,height:null,top:null,left:null},a.loginSent=!1,a.loginSuccess=!1,a.submit=function(){b.postLogin(a.form).then(function(){a.loginSuccess=!0},function(){a.loginSuccess=!1})["finally"](function(){a.loginSent=!0})},a.getUsersSent=!1,a.getUsersSuccess=!1,a.getUsers=function(){b.getUsers().then(function(){a.getUsersSuccess=!0},function(){a.getUsersSuccess=!1})["finally"](function(){a.getUsersSent=!0})},a.getPrivateUsersSent=!1,a.getPrivateUsersSuccess=!1,a.getPrivateUsers=function(){b.getPrivateUsers().then(function(){a.getPrivateUsersSuccess=!0},function(){a.getPrivateUsersSuccess=!1})["finally"](function(){a.getPrivateUsersSent=!0})},a.sendStringSent=!1,a.sendStringSuccess=!1,a.sendString=function(){b.sendString(a.stringForm).then(function(){a.sendStringSuccess=!0},function(){a.sendStringSuccess=!1})["finally"](function(){a.sendStringSent=!0})},a.sendCroppingDtoSent=!1,a.sendCroppingDtoSuccess=!1,a.sendCroppingDto=function(){b.sendCroppingDto(a.croppingDtoForm).then(function(){a.sendCroppingDtoSuccess=!0},function(){a.sendCroppingDtoSuccess=!1})["finally"](function(){a.sendCroppingDtoSent=!0})}}]),app.factory("spinnerService",[function(){"use strict";var a={};return{_register:function(b){if(!b.id)throw new Error("A spinner must have an ID to register with the spinner service!");a[b.id]=b},show:function(b){if(a.hasOwnProperty(b)){var c=a[b];c.showSpinner=!0}},hide:function(b){if(a.hasOwnProperty(b)){var c=a[b];c.showSpinner=!1}},hideAll:function(){for(var b in a)if(a.hasOwnProperty(b)){var c=a[b];c.showSpinner=!1}}}}]),app.directive("spinner",[function(){"use strict";return{restrict:"E",replace:!0,templateUrl:"spinner/spinner.directive.tpl.html",scope:{id:"@",label:"@?",showSpinner:"@?"},controller:["$scope","$attrs","spinnerService",function(a,b,c){"undefined"==typeof a.label&&(a.label=""),c._register(a)}]}}]),app.factory("signatureRestService",["$http",function(a){"use strict";return{getSignature:function(){return a.get("http://localhost:8080/api/user/test-uuid/signature",{responseType:"blob"})},postSignaturePath:function(){return"http://localhost:8080/api/user/test-uuid/signature"}}}]),app.controller("SignatureQRErrorController",["$scope","$modalInstance",function(a,b){"use strict";a.dismiss=b.dismiss}]),app.controller("SignatureQRController",["$scope","$modalInstance","$modal","signatureRestService","spinnerService",function(a,b,c,d,e){"use strict";a.qrUrl=window.location.href,a.dismiss=b.dismiss,a.checkAndClose=function(){e.show("spinnerSignatureQR");var a=d.getSignature();a.then(function(a){b.close(a)},function(){c.open({templateUrl:"signature/signature-qr-error.tpl.html",controller:"SignatureQRErrorController",size:"sm"})})["finally"](function(){e.hide("spinnerSignatureQR")})}}]),app.directive("signaturePad",["$window","$timeout","base64BinaryConverterService",function(a,b,c){"use strict";return{restrict:"E",replace:!1,templateUrl:"signature/signature-pad.directive.tpl.html",scope:{submit:"="},link:function(d,e){function f(a){return c.toBinary(a)}function g(){var a=parseInt(jQuery("#signatureCaptureImage").css("width"),10),b=parseInt(jQuery("#signatureCaptureTouch").css("width"),10),c=Math.max(a,b),e=1*c/3;h.attr("width",c),h.attr("height",e),d.signatureWidth=c,d.signatureHeight=e}var h=e.find("canvas"),i=new SignaturePad(h[0]);g(),d.clearSignature=function(){i.clear(),g()},d.submitSignature=function(){if(!i.isEmpty()){var a=i.toDataURL(),c=f(a);b(function(){d.submit(c)})}},a.addEventListener("resize",d.clearSignature,!1),a.addEventListener("orientationchange",d.clearSignature,!1)}}}]),app.controller("SignatureController",["$scope","$state","$modal","Modernizr","spinnerService","signatureRestService","base64BinaryConverterService",function(a,b,c,d,e,f,g){"use strict";function h(a){e.hide("spinnerSignatureImage"),e.hide("spinnerSignatureTouch"),b.go("cropping",{imageUri:a})}a.postSignaturePath=f.postSignaturePath(),a.Modernizr=d,a.showUploadImage=!1,a.showTouchInput=!0,a.forceSignaturePad=!1,a.flow={},a.selectTouchTab=function(){a.showUploadImage=!1,a.showTouchInput=!0},a.selectUploadTab=function(){a.showUploadImage=!0,a.showTouchInput=!1},a.submitTouch=function(b){a.flow.touch.addFile(b),a.flow.touch.upload()},a.showSpinner=function(a){e.show(a)},a.showQR=function(){var a=c.open({templateUrl:"signature/signature-qr.tpl.html",controller:"SignatureQRController"});a.result.then(function(a){g.toBase64(a.data,h)})},a.getImageAndGoToNextPage=function(){var b=a.flow.image.files[0]||a.flow.touch.files[0];g.toBase64(b.file,h)}}]),app.directive("crop",[function(){"use strict";return{restrict:"A",scope:{id:"=",dimensions:"="},link:function(a,b,c){function d(b){a.dimensions={width:b.w,height:b.h,top:b.y,left:b.x},a.$apply()}a.dimensions={};var e=parseInt(jQuery(b).parent().css("width"),10);jQuery("#"+c.id).Jcrop({onChange:d,onSelect:d,boxWidth:e})}}}]),app.factory("croppingRestService",["$http",function(a){"use strict";return{postSignatureCropping:function(b){return a.post("http://localhost:8080/user/test-uuid/signature/crop",b)}}}]),app.controller("CroppingController",["$scope","$stateParams","$state","spinnerService","croppingRestService",function(a,b,c,d,e){"use strict";function f(){c.go("dashboard")}null===b.imageUri&&c.go("signature",{},{location:"replace"}),a.imageUri=b.imageUri,a.dimensions={},a.hasDimensions=!1,a.$watch("dimensions",function(){a.hasDimensions="undefined"==typeof a.dimensions.width||a.dimensions.width<40||a.dimensions.height<30?!1:!0}),a.submit=function(){a.hasDimensions&&(d.show("spinnerCroppingSubmit"),e.postSignatureCropping(a.dimensions).then(function(){f()},function(){})["finally"](function(){d.hide("spinnerCroppingSubmit")}))}}]),app.controller("DashboardController",["$scope",function(a){"use strict";a.dashboard="This is the dashboard :))"}]);