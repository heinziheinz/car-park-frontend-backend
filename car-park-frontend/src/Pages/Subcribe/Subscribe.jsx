import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Form from "./../../Components/Form/Form.jsx";
import {initializeInputFieldsForKalender} from "./initializeInputFieldsForCalender.js"
import {checkIfAllValuesAreDefined} from "./checkIfAllValuesAreDefined.js";
import {fetchAuthenticated} from "../../Utilities/api.js";

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
        if (!allowedToSubmit) {
            return;
        }
        try {
            const data = await fetchAuthenticated("/users", {
                method: "POST",
                body: JSON.stringify(inputValue)
            });
            console.log(data);
            if (data.ok) {
                navigate("/login")
            } else {
                throw new Error("CarHouseError");
            }

        } catch (err) {
            console.error(err)
        }
    }
    const onChangeHandler = (event) => {
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
        buttonValue={"Subscribe"}
    />;
}

export default Subscribe;