// conn = new Mongo("localhost:27017");
conn = new Mongo();
db = conn.getDB("myDataBase");
db = db.getSiblingDB("admin");
db.createUser({
  user: "admin",
  pwd: "54vcc34R3cx23ewacee23d!",
  roles: ["dbAdminAnyDatabase"],
});
