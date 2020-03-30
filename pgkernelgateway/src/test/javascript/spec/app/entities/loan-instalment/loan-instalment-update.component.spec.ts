/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentUpdateComponent } from 'app/entities/loan-instalment/loan-instalment-update.component';
import { LoanInstalmentService } from 'app/entities/loan-instalment/loan-instalment.service';
import { LoanInstalment } from 'app/shared/model/loan-instalment.model';

describe('Component Tests', () => {
  describe('LoanInstalment Management Update Component', () => {
    let comp: LoanInstalmentUpdateComponent;
    let fixture: ComponentFixture<LoanInstalmentUpdateComponent>;
    let service: LoanInstalmentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LoanInstalmentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanInstalmentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanInstalmentService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoanInstalment(123);
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
        const entity = new LoanInstalment();
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
