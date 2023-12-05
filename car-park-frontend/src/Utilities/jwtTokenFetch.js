export const jwtTokenFetch = async (endpoint, options = {}, headers) => {

    return await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
        credentials: "include",
        headers: headers,
        ...options
    });

}