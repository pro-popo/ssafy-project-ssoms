import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import store from "@/store/index.js"
const Socket = {
    namespaced: true,
    state: {
    },
    getters: {

    },
    mutations: {

    },
    actions: {
        connectSocket({ dispatch }) {
            const serverURL = "http://localhost:8080/ssoms/stomp";
            let socket = new SockJS(serverURL);
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect(
                {},
                (frame) => {
                    this.connected = true;
                    frame;
                    this.stompClient.subscribe("/sendData/schedulerM", (res) => {
                        const realTimeData = JSON.parse(res.body);
                        if (realTimeData.outlier) {
                            let start = new Date().toISOString().substr(0, 10);
                            let end = new Date();
                            end.setDate(end.getDate() + 1);
                            end = end.toISOString().substr(0, 10);
                            store.dispatch('Realtime/getOutlierData', { start: '/' + start, end: '/' + end });
                        }
                        if (store.getters['Realtime/getRealTime'] !== realTimeData.time) {
                            if (store.getters['Oracle/getDatabaseCpuTimeRatioList'].length >= 12) {
                                dispatch('addRealtimeData', realTimeData);
                            } else {
                                setTimeout(
                                    function () {
                                        dispatch('addRealtimeData', realTimeData);
                                    }.bind(this),
                                    1000
                                );
                            }
                        }
                    });
                },
                (error) => {
                    // 소켓 연결 실패
                    console.log("소켓 연결 실패", error);
                    this.connected = false;
                }
            );
        },

        addRealtimeData({ rootGetters }, realTimeData) {
            if (!rootGetters['Realtime/getIsSelected']) {
                store.commit("Oracle/SET_ORACLE_STATUS_LIST", realTimeData.oracleStatus);
                store.commit("TopQuery/SET_TOPQUERY_LIST", realTimeData.allSchemaQueryInfo);

                store.commit("Realtime/SET_REALTIME", realTimeData.time);
                store.commit("Schema/SET_REALTIME_SCHEMA_LIST", realTimeData.allSchemaStastics);
            }
        },
    },
    modules: {}
};

export default Socket;
