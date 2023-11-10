import {useState} from "react";

const UserForm = ({user, onSave, authorities}) => {

    console.log(authorities)
    console.log(user)
    const [name, setName] = useState(user?.name ?? "");
    const [birthdate, setBirthdate] = useState(user?.birthdate ?? "");
    const [address, setAddress] = useState(user?.address ?? "");
    const [authority, setAuthorities] = useState(authorities ?? []);

    const onSubmit = (e) => {
        e.preventDefault();
        return onSave({name, birthdate, address, authority});
    }


    return (<>

        <form className="UserForm" onSubmit={onSubmit}>
            <div className="control">
                <label htmlFor="first-name">Name:</label>
                <input
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    name="name"
                    id="name"
                />
                <label htmlFor="price">Birthdate:</label>
                <input
                    value={birthdate}
                    onChange={(e) => setBirthdate(e.target.value)}
                    name="birthdate"
                    id="birthdate"
                    type="date"
                />
                <label htmlFor="url-link">Address:</label>
                <input
                    value={address}
                    onChange={(e) => setAddress(e.target.value)}
                    name="address"
                    id="address"
                />
            </div>
            <div className="control">
                <label>Authority:
                    <select onChange={(event) => {
                        setAuthorities(event.target.value)
                    }}>
                        <option value={"Add Authority"}
                                defaultValue={"Add Authority"}>{"Add Authority"}</option>
                        {authorities.map((value, index) => {
                            return <option key={value.authority + index}>{value.authority}</option>
                        })}
                    </select>
                </label>
            </div>
            <input type="submit" value={"Push"}/>
        </form>
    </>);
}
export default UserForm;
