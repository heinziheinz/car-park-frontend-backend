import {useState} from "react";
import {loadJson} from "../Utilities/fetch.js"

const CarForm = () => {

    const [car, setCar] = useState(null);

    const [typeName, setTypeName] = useState("");
    const [price, setPrice] = useState("");

    const onSubmit = async (e) => {
        e.preventDefault();

        const option = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest',
            },
            mode: 'cors',
            cache: 'default',
            body: JSON.stringify({typeName,price }),
        };

        const data = await loadJson(`/cars`, option)
        console.log(data);

    }

    return (
        <form className="EmployeeForm" onSubmit={onSubmit}>
            <div className="control">
                <label htmlFor="first-name">Type Name:</label>
                <input
                    value={typeName}
                    onChange={(e) => setTypeName(e.target.value)}
                    name="type-name"
                    id="type-name"
                />
                <label htmlFor="price">Type Name:</label>
                <input
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    name="price"
                    id="price"
                />
            </div>
            <input type="submit" value={"Push"} />
        </form>
    );

}
export default CarForm;