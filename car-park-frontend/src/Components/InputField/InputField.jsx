const InputField = (props) => {


    const inputHandler = (event) => {
        props.onChangeHandler(event)
    }

    return (
        <div className={props.className}>
            <label>{props.label}</label>
            <input
                onChange={inputHandler}
                type={props.type}
                name={props.name}
                value={props.value}
            />
        </div>
    )
}
export default InputField;