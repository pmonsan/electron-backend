/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { AccountFeatureUpdateComponent } from 'app/entities/account-feature/account-feature-update.component';
import { AccountFeatureService } from 'app/entities/account-feature/account-feature.service';
import { AccountFeature } from 'app/shared/model/account-feature.model';

describe('Component Tests', () => {
  describe('AccountFeature Management Update Component', () => {
    let comp: AccountFeatureUpdateComponent;
    let fixture: ComponentFixture<AccountFeatureUpdateComponent>;
    let service: AccountFeatureService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [AccountFeatureUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AccountFeatureUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountFeatureUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AccountFeatureService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AccountFeature(123);
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
        const entity = new AccountFeature();
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
