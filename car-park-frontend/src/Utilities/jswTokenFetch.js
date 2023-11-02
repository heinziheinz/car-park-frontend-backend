import {Buffer} from "buffer";

export const jswTokenFetch = async (endpoint, options = {}, headers) => {
    console.log("method")

    const response = await fetch(`http://localhost:8080${endpoint}`, {
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