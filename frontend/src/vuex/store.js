import { createStore } from 'vuex';
import auth from './modules/auth';

const store = createStore({
    modules: {
        auth,
    },
});

export default store;

// import { createStore } from 'vuex';
// import state from './state';
// import mutations from './mutations';
// import actions from './actions';
// import getters from './getters';
// import auth from './modules/auth';
//
// export default createStore({
//     state,
//     mutations,
//     actions,
//     getters,
//     modules: {
//         auth, // thêm module auth vào store
//     },
// });
