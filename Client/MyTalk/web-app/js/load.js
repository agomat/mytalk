// bootstrap application
head.js("js/loadTemplates.js");
head.js("js/app.js");

// bootstrap StateManager
head.js("js/statemanager/StateManager.js");

// bootstrap Storage
head.js("js/model/storage/IPAddressProxy.js");
head.js("js/model/storage/RequestManager.js");
head.js("js/model/storage/WebSocketConnection.js");
head.js("js/model/storage/WebSocketHandler.js");
head.js("js/model/storage/SocketAdapter.js");

// bootstrap DS.Store
head.js("js/store/Store.js");

// bootstrap DS.Model
head.js("js/model/modelstruct/ARI.js");
head.js("js/model/modelstruct/Authentication.js");
head.js("js/model/modelstruct/PersonalData.js");
head.js("js/model/modelstruct/WUser.js");
head.js("js/model/modelstruct/User.js");
head.js("js/model/modelstruct/Call.js");
head.js("js/model/modelstruct/List.js");

head.js("js/model/modelstruct/WList.js");
head.js("js/model/modelstruct/WCall.js");

// bootstrap Router
head.js("js/router/Router.js");

// bootstrap controllers
head.js("js/controller/Controller.js");

// bootstrap RTCAdapter
head.js("js/controller/communicator/PeerConnection.js");

