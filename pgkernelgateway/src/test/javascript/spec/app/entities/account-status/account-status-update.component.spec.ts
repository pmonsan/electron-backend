/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountStatusUpdateComponent } from 'app/entities/account-status/account-status-update.component';
import { AccountStatusService } from 'app/entities/account-status/account-status.service';
import { AccountStatus } from 'app/shared/model/account-status.model';

describe('Component Tests', () => {
  describe('AccountStatus Management Update Component', () => {
    let comp: AccountStatusUpdateComponent;
    let fixture: ComponentFixture<AccountStatusUpdateComponent>;
    let service: AccountStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountStatus(123);
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
        const entity = new AccountStatus();
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
