import {useState, useEffect, useContext} from "react";
import {jswTokenFetch} from "../Utilities/jswTokenFetch.js";
import {Buffer} from "buffer";
import {useNavigate} from "react-router-dom";
import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {findRole} from "../Utilities/findRole.js";
import Form from "../Components/form.jsx";

const Login = () => {
    const navigate = useNavigate();
    const {setLoggedIn} = useContext(LogginInContext);
    const {setUserRole} = useContext(UserRoleContext);
    const inputValues = {
        username: "",
        password: ""
    }
    const [inputValue, setInputValue] = useState(inputValues);
    const inputFields = [
        {
            type: "text",
            className: "first-name",
            name: "username",
            label: "User Name",
            placeholder: "Add your name",
            value: inputValue.username
        },
        {
            type: "password",
            className: "user-name",
            name: "password",
            label: "password",
            placeholder: "add your password",
            value: inputValue.password
        },
    ];


    const handleSubmit = async (event) => {
        event.preventDefault();
        const options = {
            method: "GET",
        };
        const auth = Buffer.from(inputValue.username + ":" + inputValue.password)
            .toString("base64");

        const headers = {
            "Authorization": `Basic ${auth}`,
            "Content-Type": "application/json"
        };

        try {
            const data = await jswTokenFetch("/login", options, headers);
            if (data.ok) {
                const myData = await data.json();
                localStorage.setItem("userdata", JSON.stringify(myData));
                const userData = JSON.parse(localStorage.getItem("userdata"));
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


    const onChangeHandler = (event) => {
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value,
        });
    }

    return (
        <Form handleSubmit={handleSubmit} inputFields={inputFields} onChangeHandler={onChangeHandler}/>
    )
}

export default Login;