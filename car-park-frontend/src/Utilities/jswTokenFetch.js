import {Buffer} from "buffer";

export const jswTokenFetch = async (endpoint, options = {}, headers) => {
    console.log("method")
    console.log(import.meta.env.VITE_BACKEND_URL)


    const response = await fetch(import.meta.env.VITE_BACKEND_URL + endpoint, {
        credentials: "include",
        headers: headers,
        ...options
    });
    /* if(endpoint === "/login"){
         localStorage.setItem('jwt', response);
     }*/
    console.log("HHHHHHHHHHH")
    console.log(response)
    return response;

}