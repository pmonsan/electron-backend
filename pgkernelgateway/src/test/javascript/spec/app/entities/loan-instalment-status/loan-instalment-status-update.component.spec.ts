/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { LoanInstalmentStatusUpdateComponent } from 'app/entities/loan-instalment-status/loan-instalment-status-update.component';
import { LoanInstalmentStatusService } from 'app/entities/loan-instalment-status/loan-instalment-status.service';
import { LoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

describe('Component Tests', () => {
  describe('LoanInstalmentStatus Management Update Component', () => {
    let comp: LoanInstalmentStatusUpdateComponent;
    let fixture: ComponentFixture<LoanInstalmentStatusUpdateComponent>;
    let service: LoanInstalmentStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [LoanInstalmentStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LoanInstalmentStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LoanInstalmentStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LoanInstalmentStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LoanInstalmentStatus(123);
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
        const entity = new LoanInstalmentStatus();
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
