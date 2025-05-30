let numWeightInputs = 1; //Refers to the amount of input the user has given for weight categories (initially 1)
let numAssignmentInputs = 1; //Same as above but for assignments

/*
    The following function adds more input
    to the weight form if the add button is clicked
*/
function addInputToWeightForm()
{
    const form = document.getElementById("weight_form"); //Hold the form here
    numWeightInputs++; //There is new input in the form, so increment this

    //Following is used to be able to remove the row of inputs only
    const row = document.createElement("div");
    row.className = "weight_form_row";

    //Create the new category input (identical to the first input)
    const categoryInput = document.createElement("input");
    categoryInput.type = "text";
    categoryInput.id = "category_input_for_weights_${numWeightInputs}"; //Unique ID
    categoryInput.className = "left_textfield"; 
    categoryInput.placeholder = "Insert category."
    categoryInput.size = "30";

    //Append the new category input to the row
    row.appendChild(document.createElement("br"));
    row.appendChild(categoryInput);

    //Create the new weight input (identical to the first input)
    const weightInput = document.createElement("input");
    weightInput.type = "text";
    weightInput.id = "weight_input_${numWeightInputs}"; //Unique ID
    weightInput.className = "right_textfield";
    weightInput.placeholder = "Insert weight."
    weightInput.size = "15";

    //Append the new weight input to the row
    row.appendChild(weightInput);

    //Create a new add button (identical to the first add button)
    const addButton = document.createElement("button");
    addButton.type = "button";
    addButton.className = "addButton";
    addButton.textContent = "Add"
    addButton.onclick = addInputToWeightForm;

    //Add the new add button to the row
    row.appendChild(addButton);

    //Add a new remove button
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "removeButton";
    removeButton.textContent = "Remove";
    removeButton.addEventListener("click", () =>  //Removes all elements in this row
    {
        form.removeChild(row);
    });

    //Add the new remove button to the row
    row.appendChild(removeButton);

    //Add the row to the form
    form.appendChild(row);
}

/*

    The following function adds more input
    to the assignments form if the add button is clicked
*/
function addInputToAssignmentForm()
{
    const form = document.getElementById("assignment_form"); //Hold the form here
    numAssignmentInputs++; //There is new input in the form, so increment this

    //Following is used to be able to remove the row of inputs only
    const row = document.createElement("div");
    row.className = "assignment_form_row";

    //Create the new assignment input (identical to the first input)
    const assignmentInput = document.createElement("input");
    assignmentInput.type = "text";
    assignmentInput.id = "assignment_input_${numAssignmentInputs}"; //Unique ID
    assignmentInput.className = "left_textfield"; 
    assignmentInput.placeholder = "Insert assignment."
    assignmentInput.size = "40";

    //Append the new assignment input to the row
    row.appendChild(document.createElement("br"));
    row.appendChild(assignmentInput);

    //Create the new category input (identical to the first input)
    const categoryInput = document.createElement("input");
    categoryInput.type = "text";
    categoryInput.id = "category_input_for_assignments_${numAssignmentInputs}"; //Unique ID
    categoryInput.className = "right_textfield";
    categoryInput.placeholder = "Insert category."
    categoryInput.size = "30";

    //Append the new category input to the row
    row.appendChild(categoryInput);

    //Create the new grade input (identical to the first input)
    const gradeInput = document.createElement("input");
    gradeInput.type = "text";
    gradeInput.id = "grade_input_${numAssignmentInputs}"; //Unique ID
    gradeInput.className = "right_textfield";
    gradeInput.placeholder = "Insert grade."
    gradeInput.size = "15";

    //Append the new grade input to the row
    row.appendChild(gradeInput);

    //Create a new add button (identical to the first add button)
    const addButton = document.createElement("button");
    addButton.type = "button";
    addButton.className = "addButton";
    addButton.textContent = "Add"
    addButton.onclick = addInputToAssignmentForm;

    //Add the new add button to the row
    row.appendChild(addButton);

    //Add a new remove button
    const removeButton = document.createElement("button");
    removeButton.type = "button";
    removeButton.className = "removeButton";
    removeButton.textContent = "Remove";
    removeButton.addEventListener("click", () => //Removes all elements in this row
    {
        form.removeChild(row);
    });

    //Add the new remove button to the form
    row.appendChild(removeButton);

    //Add the row to the form
    form.appendChild(row);
}

//Performs the main functionality of the GradeCalculator via Java backend
async function calculateGrade()
{
    //Create the 3 associative arrays based on the forms to send to Java
    const categoryWeights = JSON.stringify(Array.from(getWeightsMap()));
    const assignmentCategories = JSON.stringify(Array.from(getAssignmentCategoriesMap()));
    const assignmentGrades = JSON.stringify(Array.from(getAssignmentGradesMap()));

    //Send the categoryWeights String to Java
    fetch('http://localhost:8080/get-weights', 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'text/plain'
        },
        body: categoryWeights
    })

    //Send the assignmentCategories String to Java
    fetch('http://localhost:8080/get-assignment-categories', 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'text/plain'
        },
        body: assignmentCategories
    })

    //Send the assignmentGrades String to Java
    fetch('http://localhost:8080/get-assignment-grades', 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'text/plain'
        },
        body: assignmentGrades
    })

    /*
        Obtain the calculated grade from Java and create an alert 
        that shows the user their grade (or an error message for invalid input)
    */
   const response = await fetch("http://localhost:8080/calculate-grades", //Response by fetch (POST method)
   { 
        method: "GET"
   });

   const grade = await response.text(); //The actual text of the response (either grade or error message)

   /*
        Create an alert that will be placed in the HTML file
   */
   alert("Your grade was calculated to be at " + grade + "%.")
}

//Following function returns a map of the input for the weights form
function getWeightsMap()
{
    const weightsMap = new Map(); //Creates a Map of category-weight associations
    const input = document.querySelectorAll(".weight_form_row") //Obtain all input

    input.forEach(row => //Add to the map
    {
        const inputs = row.querySelectorAll("input"); //Obtain the input for this row

        if(inputs.length >= 2) //Prevent any errors
        {
            const category = inputs[0].value.trim(); //Get the category
            const weight = inputs[1].value.trim(); //Get the weight

            if(category) //Prevent null input
            {
                weightsMap.set(category, weight); //Update the map
            }
        }
    });

    //This map now represents the weights form
    return weightsMap;
}

//Following function returns a map of assignments to assignment category
function getAssignmentCategoriesMap()
{
    const categoriesMap = new Map(); //Creates a map of assignment-category associations
    const input = document.querySelectorAll(".assignment_form_row") //Obtain all input

    input.forEach(row => //Add to the map
    {
        const inputs = row.querySelectorAll("input"); //Obtain the input for this row

        if(inputs.length >= 2) //Prevent any errors
        {
            const assignment = inputs[0].value.trim(); //Get the assignment
            const category = inputs[1].value.trim(); //Get the category

            if(assignment) //Prevent null input
            {
                categoriesMap.set(assignment, category); //Update the map
            }
        }
    });

    //This map now represents the weights form
    return categoriesMap;
}

//Following function returns a map of assignments and their associated grades
function getAssignmentGradesMap()
{
    const gradesMap = new Map(); //Creates a map of assignment-grade associations
    const input = document.querySelectorAll(".assignment_form_row") //Obtain all input
    input.forEach(row => //Add to the map
    {
        const inputs = row.querySelectorAll("input"); //Obtain the input for this row

        if(inputs.length >= 2) //Prevent any errors
        {
            const assignment = inputs[0].value.trim(); //Get the assignment
            const grade = inputs[2].value.trim(); //Get the grade

            if(assignment) //Prevent null input
            {
                gradesMap.set(assignment, grade); //Update the map
            }
        }
    });

    return gradesMap;
}