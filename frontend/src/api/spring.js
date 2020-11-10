export default {
  URL: "http://localhost:8080/ssoms",
  ROUTES: {
    signin: "/nonuser/signin",
    logout: "/user/logout/",
    signup: "/nonuser/signup",

    isExistedAdmin: "/nonuser/existence/admin",
    isAdmin: "/user/authorization/",
    changeAdmin: "",
    getAllUserList: "",
    getUserVisitedHistory: "",

    getMyProfile: "/user/profile/",
    allUser: "/admin/alluser",
    updateUser: "/user/",
    deleteUser: "/user/",

    setSettingsOracleDB: "/admin/settings/oracledb/save",
    setSettingsSchema: "/admin/settings/schema/save",
    getSettingsOracleDB: "/admin/settings/oracledb",
    getSettingsSchema: "/admin/settings/schema",
    checkConOracleDB: "/admin/settings/oracledb/check",
    checkSettingsSchema: "/admin/settings/schema/check",

    scheduleStart: "/admin/realtime/start",
    scheduleStop: "/admin/realtime/stop",
    scheduleStatus: "/admin/realtime/status"
  }
};
