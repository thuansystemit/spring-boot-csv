import { Component, OnInit } from '@angular/core';
import { Student } from '../shared/student';
import  { DataService } from "../shared/data_services/data.service";
import { FormBuilder, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  students: Student[];
  uploadForm: FormGroup;

  constructor(private dataService: DataService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.getStudents();
    this.uploadForm = this.formBuilder.group({
      profile: ['']
    });

  }
  getStudents(): void {
    this.dataService.getStudents()
      .subscribe(students => this.students = students);
  }
  onFileSelect(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.uploadForm.get('profile').setValue(file);
    }
  }
  onSubmit() {
    const formData = new FormData();
    formData.append('file', this.uploadForm.get('profile').value);
    this.dataService.handleUpload(formData);
    this.getStudents();
  }

}
