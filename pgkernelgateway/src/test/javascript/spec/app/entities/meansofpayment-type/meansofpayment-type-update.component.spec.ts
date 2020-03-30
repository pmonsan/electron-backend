/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PgkernelgatewayTestModule } from '../../../test.module';
import { MeansofpaymentTypeUpdateComponent } from 'app/entities/meansofpayment-type/meansofpayment-type-update.component';
import { MeansofpaymentTypeService } from 'app/entities/meansofpayment-type/meansofpayment-type.service';
import { MeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

describe('Component Tests', () => {
  describe('MeansofpaymentType Management Update Component', () => {
    let comp: MeansofpaymentTypeUpdateComponent;
    let fixture: ComponentFixture<MeansofpaymentTypeUpdateComponent>;
    let service: MeansofpaymentTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PgkernelgatewayTestModule],
        declarations: [MeansofpaymentTypeUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(MeansofpaymentTypeUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MeansofpaymentTypeUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MeansofpaymentTypeService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MeansofpaymentType(123);
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
        const entity = new MeansofpaymentType();
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
