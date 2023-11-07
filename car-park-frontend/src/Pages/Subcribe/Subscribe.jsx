import {useEffect, useState} from "react";
import {Buffer} from "buffer";
import {jwtTokenFetch} from "../../Utilities/jwtTokenFetch.js";
import {useNavigate} from "react-router-dom";
import Form from "./../../Components/Form/Form.jsx";
import {initializeInputFieldsForKalender} from "./initializeInputFieldsForCalender.js"
import {checkIfAllValuesAreDefined} from "./checkIfAllValuesAreDefined.js";

const Subscribe = () => {
    const navigate = useNavigate();
    const inputValues = {
        name: "",
        password: "",
        birthdate: "",
        address: ""
    }
    const [inputValue, setInputValue] = useState(inputValues);
    const inputFields = initializeInputFieldsForKalender(inputValue.name, inputValue.password, inputValue.birthdate, inputValue.address);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const allowedToSubmit = checkIfAllValuesAreDefined(inputValue);
        if(!allowedToSubmit){
            return;
        }
        const options = {
            method: "POST",
            body: JSON.stringify(inputValue)
        };
        const auth = Buffer.from(inputValue.username + ":" + inputValue.password)
            .toString("base64");
        const headers = {
            "Content-Type": "application/json"
        };

        try {
            const data = await jwtTokenFetch("/users", options, headers);
            console.log(data);
            if (data.ok) {
                const myData = await data.json();
                navigate("/login")
            } else {
                throw new Error("CarHouseError");
            }

        } catch (err) {
            console.error(err)
        }
    }
    useEffect(() => {
        console.log(inputValue)
    }, [inputValue]);

    const onChangeHandler = (event) => {
        console.log(event.target.name)
        console.log(event.target.value)
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value,
        });
    }
    return <Form
        handleSubmit={handleSubmit}
        inputFields={inputFields}
        onChangeHandler={onChangeHandler}
        inputValues={inputValues}
    />;
}

export default Subscribe;