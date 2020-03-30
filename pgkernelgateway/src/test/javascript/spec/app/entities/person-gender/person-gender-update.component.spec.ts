/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { PersonGenderUpdateComponent } from 'app/entities/person-gender/person-gender-update.component';
import { PersonGenderService } from 'app/entities/person-gender/person-gender.service';
import { PersonGender } from 'app/shared/model/person-gender.model';

describe('Component Tests', () => {
  describe('PersonGender Management Update Component', () => {
    let comp: PersonGenderUpdateComponent;
    let fixture: ComponentFixture<PersonGenderUpdateComponent>;
    let service: PersonGenderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [PersonGenderUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PersonGenderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PersonGenderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PersonGenderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonGender(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new PersonGender();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
