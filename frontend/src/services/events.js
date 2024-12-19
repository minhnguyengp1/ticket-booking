import {Api} from "./api";

async function createOne(name, location, date) {
    return Api.post("/events", {name, location, date});
}

async function getOne(id) {
    return Api.get(`/events/${id}`);
}

async function getAll() {
    return Api.get("/events");
}

async function updateOne(id, name, location, date) {
    return Api.put(`/events/${id}`, {name, location, date});
}

async function deleteOne(id) {
    return Api.delete(`/events/${id}`);
}

const eventService = {
    createOne,
    getOne,
    getAll,
    updateOne,
    deleteOne,
};

export {eventService};
