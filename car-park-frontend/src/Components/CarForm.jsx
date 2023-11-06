import {useState} from "react";
import {loadJson} from "../Utilities/loadJson.js"

const CarForm = ({onSave}) => {
    const [car, setCar] = useState(null);

    const [typeName, setTypeName] = useState("");
    const [price, setPrice] = useState("");
    const [image, setImage] = useState("");

    const onSubmit = (e) => {

        e.preventDefault();
        return onSave({typeName, price, image});

        /*const option = {
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
        console.log(data);*/

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
                <label htmlFor="price">Price:</label>
                <input
                    value={price}
                    onChange={(e) => setPrice(e.target.value)}
                    name="price"
                    id="price"
                />
                <label htmlFor="url-link">Image:</label>
                <input
                    value={image}
                    onChange={(e) => setImage(e.target.value)}
                    name="url-link"
                    id="url-link"
                />
            </div>
            <input type="submit" value={"Push"}/>
        </form>
    );

}
export default CarForm;