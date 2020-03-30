export interface IPersonGender {
  id?: number;
  code?: string;
  label?: string;
  active?: boolean;
}

export class PersonGender implements IPersonGender {
  constructor(public id?: number, public code?: string, public label?: string, public active?: boolean) {
    this.active = this.active || false;
  }
}
