/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonGenderDetailComponent } from 'app/entities/person-gender/person-gender-detail.component';
import { PersonGender } from 'app/shared/model/person-gender.model';

describe('Component Tests', () => {
  describe('PersonGender Management Detail Component', () => {
    let comp: PersonGenderDetailComponent;
    let fixture: ComponentFixture<PersonGenderDetailComponent>;
    const route = ({ data: of({ personGender: new PersonGender(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonGenderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(PersonGenderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PersonGenderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.personGender).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
