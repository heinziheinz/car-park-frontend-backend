export const fetchAuthenticated = async (endpoint, options) => {

    return await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
        credentials: "include",
        ...options,
        headers: {
            "Content-Type": "application/json",
            ...options.headers
        },
    });

}




