/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { CustomerBlacklistUpdateComponent } from 'app/entities/customer-blacklist/customer-blacklist-update.component';
import { CustomerBlacklistService } from 'app/entities/customer-blacklist/customer-blacklist.service';
import { CustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

describe('Component Tests', () => {
  describe('CustomerBlacklist Management Update Component', () => {
    let comp: CustomerBlacklistUpdateComponent;
    let fixture: ComponentFixture<CustomerBlacklistUpdateComponent>;
    let service: CustomerBlacklistService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [CustomerBlacklistUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CustomerBlacklistUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CustomerBlacklistUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CustomerBlacklistService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CustomerBlacklist(123);
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
        const entity = new CustomerBlacklist();
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
