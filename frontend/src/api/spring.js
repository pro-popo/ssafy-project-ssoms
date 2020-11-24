var serverURL = window.location.hostname;
export default {
  URL: `http://${serverURL}:8080/ssoms`,
  ROUTES: {
    signin: "/nonuser/signin",
    logout: "/user/logout/",
    signup: "/nonuser/signup",

    // admin
    isExistedAdmin: "/nonuser/existence/admin",
    isAdmin: "/user/authorization/",
    changeAdmin: "/admin/change",
    deleteMember: "/admin/delete",

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
    getAllSchemaList: "/admin/settings/schema/all",

    scheduleStart: "/admin/realtime/start",
    scheduleStop: "/admin/realtime/stop",
    scheduleStatus: "/admin/realtime/status",

    getRealTimeData: "/data/realtime",
    getPastData: "/data/past"
  }
};
