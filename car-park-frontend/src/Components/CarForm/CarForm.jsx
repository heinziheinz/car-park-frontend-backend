import {useState} from "react";
import {loadJson} from "../../Utilities/loadJson.js"

const CarForm = ({carHouse, onSave}) => {
    const [typeName, setTypeName] = useState(carHouse?.typeName ?? "");
    const [price, setPrice] = useState(carHouse?.price ?? "");
    const [image, setImage] = useState(carHouse?.image ?? "");

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({typeName, price, image});

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