import {useState} from "react";

const CarForm = ({car, onSave}) => {
    const [typeName, setTypeName] = useState(car?.typeName ?? "");
    const [price, setPrice] = useState(car?.price ?? "");
    const [image, setImage] = useState(car?.image ?? "");

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({typeName, price, image});

    }

    return (
        <div className="carhouseWrapper">
            <form className="styleForm"  onSubmit={onSubmit}>
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
        </div>
    );

}
export default CarForm;