import {useState, useEffect, useContext} from "react";
import {Buffer} from "buffer";
import {useNavigate} from "react-router-dom";
import {LogginInContext} from "../main.jsx";
import {UserRoleContext} from "../main.jsx";
import {findRole} from "../Utilities/findRole.js";
import Form from "../Components/Form/Form.jsx";
import {fetchAuthenticated} from "../Utilities/api.js";

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
            type: "email",
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
        const auth = Buffer.from(inputValue.username + ":" + inputValue.password)
            .toString("base64");
        const headers = {
            "Authorization": `Basic ${auth}`,
            "Content-Type": "application/json"
        };

        try {
            const data = await fetchAuthenticated("/login", {
                method: "GET",
                headers
            });
            if (data.ok) {
                const myData = await data.json();
                localStorage.setItem("userdata", JSON.stringify(myData));
                const roleContext = findRole(myData.authorities);
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
        <Form
            handleSubmit={handleSubmit}
            inputFields={inputFields}
            onChangeHandler={onChangeHandler}
            buttonValue={"Login"}
        />
    )
}

export default Login;