import {useState, useEffect, useContext} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import {Buffer} from "buffer";
import {useNavigate} from "react-router-dom";
import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {findRole} from "../Utilities/findRole.js";

const Login = () => {
    const navigate = useNavigate();
    const {loggedIn, setLoggedIn} = useContext(LogginInContext);
    const {userRole, setUserRole} = useContext(UserRoleContext);
    const inputValues = {
        username: "",
        password: ""
    }

    const [submitted, setSubmitted] = useState(false);
    const [inputValue, setInputValue] = useState(inputValues);

    const handleSubmit = async (event) => {
        event.preventDefault();

        setSubmitted(true);
        const options = {
            method: "GET",
        };
        const auth = Buffer.from(inputValue.username + ":" + inputValue.password)
            .toString("base64");

        const headers = {
            "Authorization":  `Basic ${auth}`,
            "Content-Type": "application/json" };

        try {
            const data = await jswTokenFetch("/login", options, headers);
            if (data.ok) {
                const myData = await data.json();
                localStorage.setItem("userdata", JSON.stringify(myData));
                const roleContext = findRole(myData.authorities);
                console.log(roleContext)
                setUserRole(roleContext);
                setLoggedIn(true);
                navigate("/")

            }

        } catch (err) {
            console.log(err)
        }

    }

    const handleLogin = (event) => {
        event.preventDefault();
        const headers = new Headers();
        const auth = Buffer.from("Paul" + ":" + "123")
            .toString("base64");
        headers.set("Authorization", "Basic " + auth);
        return fetch("http://localhost:8080/login", {method: "GET", headers: headers})
            .then((response) => response.text())
            .then(jwt => {
                console.log('JWT ' + jwt)

            })
            .catch((error) => console.log("ERROR: " + error))

    }


    const onChangeHandler = (event) => {
        console.log(event.target.value)
        console.log(event.target.name)
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value,
        });
    }

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="username" onChange={onChangeHandler} placeholder={'name'}/>
            <input type="password" name="password" onChange={onChangeHandler} placeholder={'password'}/>
            <input type="submit" value={'submit'}/>
        </form>
    )
    //return <div>This is a Login</div>;
}

export default Login;