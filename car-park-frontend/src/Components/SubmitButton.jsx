const SubmitButton = ({disabled}) => {
    return (
        <input type="submit" value={props.value} disabled={disabled}/>
    )
}

export default SubmitButton;