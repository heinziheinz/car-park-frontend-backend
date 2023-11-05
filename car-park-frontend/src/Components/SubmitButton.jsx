const SubmitButton = ({disabled, value}) => {
    return (
        <input type="submit" value={value} disabled={disabled}/>
    )
}

export default SubmitButton;