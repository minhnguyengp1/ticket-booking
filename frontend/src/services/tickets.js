import {Api} from "./api";

async function createOne(eventId, userId) {
    return Api.post(`/tickets?userId=${userId}`, { eventId });
}

async function getOne(ticketId, userId) {
    return Api.get(`/tickets/${ticketId}?userId=${userId}`);
}

async function getAll(userId) {
    return Api.get(`/tickets?userId=${userId}`);
}

async function validateOne(ticketId, ownerId) {
    return Api.post("/tickets/validate", {ticketId, ownerId});
}

const ticketService = {
    createOne,
    getOne,
    getAll,
    validateOne,
};

export {ticketService};

