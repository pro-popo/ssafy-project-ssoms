export default {
  URL: "http://localhost:8080/ssoms",
  ROUTES: {
    signin: "/nonuser/signin",
    logout: "/user/logout/",
    signup: "/nonuser/signup",

    isExistedAdmin: "/nonuser/existence/admin",
    idAdmin: "/user/authorization/",
    changeAdmin: "",
    getAllUserList: "",
    getUserVisitedHistory: "",

    getMyProfile: "/user/profile/",
    allUser: "/admin/alluser",
    updateUser: "/user/",
    deleteUser: "/user/",

    setSettingsOracleDB: "/admin/settings/OracleDB/save",
    setSettingsSchema: "/admin/settings/Schema/save",
    getSettingsOracleDB: "/admin/settings/OracleDB",
    getSettingsSchema: "/admin/settings/Schema"
  }
};
