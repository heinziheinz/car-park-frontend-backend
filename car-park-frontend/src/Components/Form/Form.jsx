import InputField from "../InputField/InputField.jsx";
import SubmitButton from "../SubmitButton/SubmitButton.jsx";
//buttonValue
const Form = ({handleSubmit, inputFields, onChangeHandler, children, disabled, buttonValue}) => {

    return (
        <div className="formColor">
            <form onSubmit={handleSubmit}>
                {children}
                {
                    inputFields.map((fields, index) => {
                        console.log(fields.value)
                        return (
                            <div key={"wrapper" + index}>
                                <InputField
                                    key={index}
                                    name={fields.name}
                                    className={fields.className}
                                    label={fields.label}
                                    type={fields.type}
                                    placeholder={fields.placeholder}
                                    onChangeHandler={onChangeHandler}
                                    value={fields.value}
                                />
                            </div>
                        );
                    })
                }
                <SubmitButton value={buttonValue} disabled={disabled}/>
            </form>
        </div>
    );
}
export default Form;