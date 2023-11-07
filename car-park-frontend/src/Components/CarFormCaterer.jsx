import {useState} from "react";
import Form from "./form.jsx"

const CarFromCaterer = ({car, onSave}) => {

    const inputValues = {
        typeName: "",
        price: "",
        image:""
    }
    const [inputValue, setInputValue] = useState(inputValues);

    const [typeName, setTypeName] = useState(car?.typeName ?? "");
    const [price, setPrice] = useState(car?.price ?? "");
    const [image, setImage] = useState(car?.image ?? "");
//TODO: onSave and Sates are being passed down to CarForm
    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({typeName, price, image});

    }
    const onChangeHandler = (event) => {
        setInputValue({
            ...inputValue,
            [event.target.name]: event.target.value,
        });
    }

    return <Form
        car={car}
        onSave={onSubmit}
        onChangeHandler={ onChangeHandler}
    />
}
export default CarFromCaterer;